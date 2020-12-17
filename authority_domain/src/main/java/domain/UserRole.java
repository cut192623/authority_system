package domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("t_user_role")
@Data
public class UserRole  {

  private long id;
  private String userId;
  private String roleId;



}
