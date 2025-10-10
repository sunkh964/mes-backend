package com.example.mes_backend.service;


import com.example.mes_backend.repository.BlockQCRepository;
import com.example.mes_backend.repository.QualityControlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MesDashboardService {

    //API URL
    @Value("${api.proxy.base-url:http://localhost:8083/api/proxy/dashboard/}")
    private String API_BASE_URL;

    private final BlockQCRepository blockQCRepository;
    private final QualityControlRepository qualityControlRepository;

    final double EPSILON = 0.0001;
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getErpDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();

        try {
            // API 서버 호출
            String url = API_BASE_URL + "projects";
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> projectList = response.getBody();

            if (projectList == null || projectList.isEmpty()) {
                summary.put("projectCount", 0);
                summary.put("inProgressProjects", 0);
                summary.put("completedProjects", 0);
                summary.put("avgProgressRate", 0);
                return summary;
            }

            int totalCount = projectList.size();
            int inProgress = 0;
            int completed = 0;
            double totalProgress = 0.0;

            for (Map<String, Object> project : projectList) {
                Object rateObj = project.get("progressRate");
                double rate = 0.0;

                if (rateObj != null) {
                    try {
                        rate = Double.parseDouble(rateObj.toString());
                    } catch (NumberFormatException ignored) {}
                }

                totalProgress += rate;

                // 99.9999 ~ 100.0001 범위도 "완료"로 간주
                if (Math.abs(rate - 100.0) < EPSILON || rate > 100.0) {
                    completed++;
                } else {
                    inProgress++;
                }
            }

            double avg = Math.round((totalProgress / totalCount) * 100.0) / 100.0;

            summary.put("projectCount", totalCount);
            summary.put("inProgressProjects", inProgress);
            summary.put("completedProjects", completed);
            summary.put("avgProgressRate", avg);

        } catch (Exception e) {
            System.err.println("MES 대시보드 계산 실패: " + e.getMessage());
            summary.put("projectCount", 0);
            summary.put("inProgressProjects", 0);
            summary.put("completedProjects", 0);
            summary.put("avgProgressRate", 0);
        }

        return summary;
    }

    // 2. 고객사 현황 (ERP)
    public Map<String, Object> getErpDashboardCustomer() {
        Map<String, Object> summary = new HashMap<>();

        try {
            String url = API_BASE_URL + "customers";
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> customers = response.getBody();
            if (customers == null || customers.isEmpty()) {
                summary.put("totalCustomers", 0);
                summary.put("contractCount", 0);
                summary.put("activeCount", 0);
                return summary;
            }

            int totalCustomers = customers.size();
            long contractCount = customers.stream().filter(c -> c.get("contractDate") != null).count();
            long activeCount = customers.stream()
                    .filter(c -> "ACTIVE".equalsIgnoreCase(String.valueOf(c.get("status"))))
                    .count();

            summary.put("totalCustomers", totalCustomers);
            summary.put("contractCount", contractCount);
            summary.put("activeCount", activeCount);

        } catch (Exception e) {
            System.err.println("MES 대시보드(고객사) 오류: " + e.getMessage());
            summary.put("totalCustomers", 0);
            summary.put("contractCount", 0);
            summary.put("activeCount", 0);
        }

        return summary;
    }

    // 3. 자재 현황 (ERP)
    public Map<String, Object> getErpDashboardMaterial() {
        Map<String, Object> summary = new HashMap<>();

        try {
            // API 서버 호출
            String url = API_BASE_URL + "materials";
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> materials = response.getBody();

            // 데이터가 없을 경우 기본값 리턴
            if (materials == null || materials.isEmpty()) {
                summary.put("totalMaterials", 0);
                summary.put("activeMaterials", 0);
                summary.put("categoryStats", Map.of());
                return summary;
            }

            // 총 개수
            int total = materials.size();

            // 활성 자재
            long active = materials.stream()
                    .filter(m -> m.get("status") != null && Integer.parseInt(m.get("status").toString()) == 0)
                    .count();

            // 카테고리별 통계
            Map<String, Long> categoryStats = materials.stream()
                    .filter(m -> m.get("category") != null)
                    .collect(Collectors.groupingBy(
                            m -> String.valueOf(m.get("category")),
                            Collectors.counting()
                    ));

            // 요약 결과 담기
            summary.put("totalMaterials", total);
            summary.put("activeMaterials", active);
            summary.put("categoryStats", categoryStats);

        } catch (Exception e) {
            System.err.println("MES 대시보드(자재) 오류: " + e.getMessage());
            summary.put("totalMaterials", 0);
            summary.put("activeMaterials", 0);
            summary.put("categoryStats", Map.of());
        }

        return summary;
    }

    public Map<String, Object> getErpDashboardPurchase() {
        Map<String, Object> summary = new HashMap<>();

        try {
            String url = API_BASE_URL + "purchaseOrders";  // API 서버 통해 ERP 호출
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> orders = response.getBody();
            if (orders == null || orders.isEmpty()) {
                summary.put("totalOrders", 0);
                summary.put("inProgressOrders", 0);
                summary.put("completedOrders", 0);
                summary.put("supplierStats", Map.of());
                return summary;
            }

            int totalOrders = orders.size();
            long completed = orders.stream()
                    .filter(o -> o.get("status") != null && Integer.parseInt(o.get("status").toString()) == 2) // 예: 2=완료
                    .count();
            long inProgress = orders.stream()
                    .filter(o -> o.get("status") != null && Integer.parseInt(o.get("status").toString()) == 1) // 예: 1=진행
                    .count();

            // 공급업체별 발주 건수
            Map<String, Long> supplierStats = orders.stream()
                    .filter(o -> o.get("supplierName") != null)
                    .collect(Collectors.groupingBy(
                            o -> o.get("supplierName").toString(),
                            Collectors.counting()
                    ));

            // 공급업체 TOP 5만 정렬
            Map<String, Long> topSuppliers = supplierStats.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(5)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            summary.put("totalOrders", totalOrders);
            summary.put("inProgressOrders", inProgress);
            summary.put("completedOrders", completed);
            summary.put("supplierStats", topSuppliers);

        } catch (Exception e) {
            System.err.println("MES 대시보드(구매 현황) 오류: " + e.getMessage());
            summary.put("totalOrders", 0);
            summary.put("inProgressOrders", 0);
            summary.put("completedOrders", 0);
            summary.put("supplierStats", Map.of());
        }

        return summary;
    }

    public Map<String, Object> getErpDashboardProductionPlan() {
        Map<String, Object> summary = new HashMap<>();
        String url = API_BASE_URL + "project_plans";  // API 서버 통해 ERP 호출
        try {
            //API 서버 호출 (ERP의 project_plans)
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> planList = response.getBody();
            if (planList == null || planList.isEmpty()) {
                summary.put("totalPlans", 0);
                summary.put("inProgressPlans", 0);
                summary.put("completedPlans", 0);
                summary.put("avgProgressRate", 0);
                return summary;
            }

            int total = planList.size();
            int completed = 0;
            int inProgress = 0;
            double totalProgress = 0.0;

            for (Map<String, Object> plan : planList) {
                Object rateObj = plan.get("progressRate");
                double rate = 0.0;
                if (rateObj != null) {
                    try {
                        rate = Double.parseDouble(rateObj.toString());
                    } catch (NumberFormatException ignored) {}
                }

                totalProgress += rate;

                // 100% 도달 시 완료로 판단
                if (Math.abs(rate - 100.0) < 0.0001 || rate > 100.0) {
                    completed++;
                } else {
                    inProgress++;
                }
            }

            double avgProgress = Math.round((totalProgress / total) * 100.0) / 100.0;

            summary.put("totalPlans", total);
            summary.put("inProgressPlans", inProgress);
            summary.put("completedPlans", completed);
            summary.put("avgProgressRate", avgProgress);

        } catch (Exception e) {
            System.err.println("ERP 생산계획 요약 호출 실패: " + e.getMessage());
            summary.put("totalPlans", 0);
            summary.put("inProgressPlans", 0);
            summary.put("completedPlans", 0);
            summary.put("avgProgressRate", 0);
        }

        return summary;
    }
    public Map<String, Object> getQualitySummary() {
        Map<String, Object> summary = new HashMap<>();

        try {
            // 블록 품질검사 통계 (Repository 수정 없이)
            List<?> allBlocks = blockQCRepository.findAll();
            long totalBlock = allBlocks.size();
            long passedBlock = allBlocks.stream()
                    .filter(obj -> {
                        try {
                            Object result = obj.getClass().getMethod("getResult").invoke(obj);
                            return "PASS".equalsIgnoreCase(String.valueOf(result));
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .count();

            long failedBlock = allBlocks.stream()
                    .filter(obj -> {
                        try {
                            Object result = obj.getClass().getMethod("getResult").invoke(obj);
                            return "FAIL".equalsIgnoreCase(String.valueOf(result));
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .count();

            double blockPassRate = totalBlock > 0
                    ? Math.round(((double) passedBlock / totalBlock) * 10000) / 100.0
                    : 0.0;

            // 자재 품질검사 통계
            List<?> allMaterials = qualityControlRepository.findAll();
            long totalMaterial = allMaterials.size();
            long passedMaterial = allMaterials.stream()
                    .filter(obj -> {
                        try {
                            Object result = obj.getClass().getMethod("getResult").invoke(obj);
                            return "PASS".equalsIgnoreCase(String.valueOf(result));
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .count();

            long failedMaterial = allMaterials.stream()
                    .filter(obj -> {
                        try {
                            Object result = obj.getClass().getMethod("getResult").invoke(obj);
                            return "FAIL".equalsIgnoreCase(String.valueOf(result));
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .count();

            double materialPassRate = totalMaterial > 0
                    ? Math.round(((double) passedMaterial / totalMaterial) * 10000) / 100.0
                    : 0.0;

            // 결과 매핑
            summary.put("block", Map.of(
                    "total", totalBlock,
                    "passed", passedBlock,
                    "failed", failedBlock,
                    "passRate", blockPassRate
            ));

            summary.put("material", Map.of(
                    "total", totalMaterial,
                    "passed", passedMaterial,
                    "failed", failedMaterial,
                    "passRate", materialPassRate
            ));

        } catch (Exception e) {
            System.err.println("MES 대시보드(품질검사) 오류: " + e.getMessage());
            summary.put("block", Map.of("total", 0, "passed", 0, "failed", 0, "passRate", 0));
            summary.put("material", Map.of("total", 0, "passed", 0, "failed", 0, "passRate", 0));
        }

        return summary;
    }

    // 7. 출하 현황 (ERP 출하 테이블 기반)
    public Map<String, Object> getShipmentSummary() {
        Map<String, Object> summary = new HashMap<>();
        String url = API_BASE_URL + "sales_orders"; // ERP API 서버 연동

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> shipments = response.getBody();

            if (shipments == null || shipments.isEmpty()) {
                summary.put("totalShipments", 0);
                summary.put("registered", 0);
                summary.put("inProgress", 0);
                summary.put("completed", 0);
                summary.put("passRate", 0.0);
                return summary;
            }

            int total = shipments.size();

            // status / sales_status / order_status 중 존재하는 필드 사용
            Function<Map<String, Object>, Integer> getStatus = s -> {
                Object statusObj = s.get("status") != null
                        ? s.get("status")
                        : (s.get("sales_status") != null ? s.get("sales_status") : s.get("order_status"));
                if (statusObj == null) return -1;
                try {
                    return Integer.parseInt(statusObj.toString());
                } catch (NumberFormatException e) {
                    return -1;
                }
            };

            long registered = shipments.stream().filter(s -> getStatus.apply(s) == 0).count();
            long inProgress = shipments.stream().filter(s -> getStatus.apply(s) == 1).count();
            long completed = shipments.stream().filter(s -> getStatus.apply(s) == 2).count();

            // 판매 완료율 계산
            double passRate = total > 0
                    ? Math.round(((double) completed / total) * 10000) / 100.0
                    : 0.0;

            // 결과 매핑
            summary.put("totalShipments", total);
            summary.put("registered", registered);
            summary.put("inProgress", inProgress);
            summary.put("completed", completed);
            summary.put("passRate", passRate);

        } catch (Exception e) {
            System.err.println("MES 대시보드(출하) 오류: " + e.getMessage());
            summary.put("totalShipments", 0);
            summary.put("registered", 0);
            summary.put("inProgress", 0);
            summary.put("completed", 0);
            summary.put("passRate", 0.0);
        }

        return summary;
    }



    // 8. 인사 현황 (ERP 인사/사원 테이블 기반)
    public Map<String, Object> getEmployeeSummary() {
        Map<String, Object> summary = new HashMap<>();
        String url = API_BASE_URL + "employees"; // ERP API 서버 연동 시

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    new ParameterizedTypeReference<>() {}
            );

            List<Map<String, Object>> employees = response.getBody();

            if (employees == null || employees.isEmpty()) {
                summary.put("totalEmployees", 0);
                summary.put("activeEmployees", 0);
                summary.put("inactiveEmployees", 0);
                summary.put("onLeaveEmployees", 0);
                summary.put("departments", Map.of());
                summary.put("activeRate", 0.0);
                return summary;
            }

            int total = employees.size();

            // ✅ status = 0 → 재직
            long active = employees.stream()
                    .filter(e -> {
                        Object statusObj = e.get("status");
                        if (statusObj == null) return false;
                        try {
                            return Integer.parseInt(statusObj.toString()) == 0;
                        } catch (NumberFormatException ex) {
                            return false;
                        }
                    })
                    .count();

            // status = 1 → 퇴직
            long inactive = employees.stream()
                    .filter(e -> {
                        Object statusObj = e.get("status");
                        if (statusObj == null) return false;
                        try {
                            return Integer.parseInt(statusObj.toString()) == 1;
                        } catch (NumberFormatException ex) {
                            return false;
                        }
                    })
                    .count();

            // status = 2 → 휴직
            long onLeave = employees.stream()
                    .filter(e -> {
                        Object statusObj = e.get("status");
                        if (statusObj == null) return false;
                        try {
                            return Integer.parseInt(statusObj.toString()) == 2;
                        } catch (NumberFormatException ex) {
                            return false;
                        }
                    })
                    .count();

            // 부서별 인원수 집계
            Map<String, Long> departments = employees.stream()
                    .filter(e -> e.get("departmentName") != null)
                    .collect(Collectors.groupingBy(
                            e -> String.valueOf(e.get("departmentName")),
                            Collectors.counting()
                    ));

            // 재직률 (전체 중 재직자의 비율)
            double activeRate = 0.0;

            if (total > 0) {
                double ratio = (double) active / total;
                activeRate = Math.round(ratio * 10000) / 100.0;  // 소수점 둘째 자리까지
            }

            //결과 반환 맵 구성
            summary.put("totalEmployees", total);
            summary.put("activeEmployees", active);
            summary.put("inactiveEmployees", inactive);
            summary.put("onLeaveEmployees", onLeave);
            summary.put("departments", departments);
            summary.put("activeRate", activeRate);

        } catch (Exception e) {
            System.err.println("MES 대시보드(인사) 오류: " + e.getMessage());
            summary.put("totalEmployees", 0);
            summary.put("activeEmployees", 0);
            summary.put("inactiveEmployees", 0);
            summary.put("onLeaveEmployees", 0);
            summary.put("departments", Map.of());
            summary.put("activeRate", 0.0);
        }

        return summary;
    }



}