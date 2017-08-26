package com.example.demo.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.apache.poi.ss.usermodel.CellStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Order implements Serializable {

	private static final long serialVersionUID = -1835021583969266196L;

	// @GenericGenerator(name = "system-uuid", strategy = "uuid")//
	// 声明一个策略通用生成器，name为"system-uuid",策略strategy为"uuid"。
	// @GeneratedValue(generator = "system-uuid")// 用generator属性指定要使用的策略生成器。
	// 适合String 类型的ID

	/**
	 * 订单ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ExcelField(title="订单ID", order=1)
	private Long orderId;

	/**
	 * 产品名称
	 */
	@NotEmpty(message = "产品名称不可以为空")
	@Size(max = 30, message = "产品名称不可以超过30个字符")
	@ExcelField(title="产品名称", order=2)
	private String produceName;

	/**
	 * 生产日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso=ISO.DATE)
	@Past(message = "必须是过往的时间")
	@ExcelField(title="生产日期", order=3)
	private Date productDate;

	/**
	 * 保质期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future(message = "必须是未来的时间")
	@ExcelField(title="保质期", order=4)
	private Date qualityGuaranteePeriod;

	/**
	 * 库存
	 */
	@NotNull
	@ExcelField(title="库存量", order=5)
	private Integer stockAmount;
	
	
	@ExcelField(title="单价", order=6,textAlign=CellStyle.ALIGN_RIGHT, numberPattern="￥#,###,##0.000")
	private Double price;
	
	
	private String headPortrait;
	
	
	private String imageName;

}
