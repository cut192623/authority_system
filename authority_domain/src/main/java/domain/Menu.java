package domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@TableName("t_menu")
@Data
public class Menu  {

  private String id;
  private String pid;
  private String menuName;
  private long menuType;
  private String menuImg;
  private String permiss;
  private String url;
  private String functionImg;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private String seq;

  @TableField(exist = false)
  private List<Menu> nodes;


}
