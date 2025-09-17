package com.example.mes_backend.repository;

import com.example.mes_backend.entity.MaterialInputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaterialInputRepository
        extends JpaRepository<MaterialInputEntity, Integer>,
        JpaSpecificationExecutor<MaterialInputEntity> {

//    // --- 1가지 조건 ---
//    List<MaterialInputEntity> findByWorkResult_ResultId(Integer resultId); // ✅ 수정됨
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderId(Integer workOrderId); // 작업지시 ID
//    List<MaterialInputEntity> findByMaterial_MaterialId(Integer materialId); // 자재 ID
//    List<MaterialInputEntity> findByWarehouse(String warehouse); // 창고
//    List<MaterialInputEntity> findByInputDateBetween(LocalDateTime start, LocalDateTime end); // 사용일 기간
//
//    // --- 2가지 조건 ---
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialId(Integer workOrderId, Integer materialId);
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndWarehouse(Integer workOrderId, String warehouse);
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndInputDateBetween(Integer workOrderId, LocalDateTime start, LocalDateTime end);
//    List<MaterialInputEntity> findByMaterial_MaterialIdAndWarehouse(Integer materialId, String warehouse);
//    List<MaterialInputEntity> findByMaterial_MaterialIdAndInputDateBetween(Integer materialId, LocalDateTime start, LocalDateTime end);
//    List<MaterialInputEntity> findByWarehouseAndInputDateBetween(String warehouse, LocalDateTime start, LocalDateTime end);
//
//    // --- 3가지 조건 ---
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndWarehouse(Integer workOrderId, Integer materialId, String warehouse);
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndInputDateBetween(Integer workOrderId, Integer materialId, LocalDateTime start, LocalDateTime end);
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndWarehouseAndInputDateBetween(Integer workOrderId, String warehouse, LocalDateTime start, LocalDateTime end);
//    List<MaterialInputEntity> findByMaterial_MaterialIdAndWarehouseAndInputDateBetween(Integer materialId, String warehouse, LocalDateTime start, LocalDateTime end);
//
//    // --- 4가지 조건 ---
//    List<MaterialInputEntity> findByWorkOrder_WorkOrderIdAndMaterial_MaterialIdAndWarehouseAndInputDateBetween(
//            Integer workOrderId, Integer materialId, String warehouse, LocalDateTime start, LocalDateTime end
//    );
}
