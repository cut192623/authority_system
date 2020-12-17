package szx;

import com.alibaba.druid.util.StringUtils;
import domain.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;

public class ShiroUtils {
    public static User encrypt(User user){
        if (StringUtils.isEmpty(user.getSalt())) {
            user.setSalt(UUIDUtils.getID());
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserName() + user.getSalt());
        Object obj = new SimpleHash(ConstantUtils.MD5, user.getPassword(), credentialsSalt, ConstantUtils.HASHITERATIONS);
        user.setPassword(obj.toString());
        return user;
    }
}
