package domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@TableName("t_user")
@Data
public class User implements Serializable{

  private String id;
  private String userName;
  private String password;
  private String salt;
  private String nickname;
  private String userImg;
  private String tel;
  private long sex;
  private String email;
  private String status;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;



}
