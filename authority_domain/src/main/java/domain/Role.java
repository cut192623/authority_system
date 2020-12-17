package domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("t_role")
@Data
public class Role  {

  private String id;
  private String role;
  private String remark;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;



}
