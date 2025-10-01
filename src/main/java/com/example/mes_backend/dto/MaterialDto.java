//package com.example.mes_backend.dto;
//
////import com.example.mes_backend.entity.MaterialEntity;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import lombok.Data; // Lombok @Data 어노테이션 import
//import java.time.LocalDateTime;
//
//@Data // Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor를 모두 포함
//public class MaterialDto {
//
//    private Integer materialId;
//    private String materialCode;
//    private String materialNm;
//    private String category;
//    private String specification;
//    private String unit;
//    private Double unitPrice;
//    private String warehouse;
//    private String location;
//    private String remark;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    /* DTO를 Entity로 변환하는 메소드     */
//    public MaterialEntity toEntity() {
//        MaterialEntity entity = new MaterialEntity();
//        entity.setMaterialId(this.materialId);
//        entity.setMaterialCode(this.materialCode);
//        entity.setMaterialNm(this.materialNm);
//        entity.setCategory(this.category);
//        entity.setSpecification(this.specification);
//        entity.setUnit(this.unit);
//        entity.setUnitPrice(this.unitPrice);
//        entity.setWarehouse(this.warehouse);
//        entity.setLocation(this.location);
//        entity.setRemark(this.remark);
//        return entity;
//    }
//
//    /*   Entity를 DTO로 변환하는 정적(static) 메소드   */
//    public static MaterialDto fromEntity(MaterialEntity entity) {
//        MaterialDto dto = new MaterialDto();
//        dto.setMaterialId(entity.getMaterialId());
//        dto.setMaterialCode(entity.getMaterialCode());
//        dto.setMaterialNm(entity.getMaterialNm());
//        dto.setCategory(entity.getCategory());
//        dto.setSpecification(entity.getSpecification());
//        dto.setUnit(entity.getUnit());
//        dto.setUnitPrice(entity.getUnitPrice());
//        dto.setWarehouse(entity.getWarehouse());
//        dto.setLocation(entity.getLocation());
//        dto.setRemark(entity.getRemark());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setUpdatedAt(entity.getUpdatedAt());
//        return dto;
//    }
//}
