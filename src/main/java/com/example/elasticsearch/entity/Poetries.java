package com.example.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 【请填写功能名称】对象 poetries
 * 
 * @author @Dog_Elder
 * @date 2023-03-19
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Document(indexName = "poetries")
public class Poetries
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    @Id
    private Long id;

    /** $column.columnComment */
    @Field(type = FieldType.Text)
    private Long poetId;

    /** $column.columnComment */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    /** $column.columnComment */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /** $column.columnComment */
    @Field(type = FieldType.Date)
    private Date createdAt;

    /** $column.columnComment */
    @Field(type = FieldType.Date)
    private Date updatedAt;




}
