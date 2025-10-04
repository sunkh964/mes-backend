package com.example.mes_backend.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MesDashboardService {

    @Value("${api.server.url:http://localhost:8083/api/proxy/dashboard/projects}")
    private String API_SERVER_URL;
    final double EPSILON = 0.0001;
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getErpDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();

        try {
            // API 서버 호출
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    API_SERVER_URL,
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
}