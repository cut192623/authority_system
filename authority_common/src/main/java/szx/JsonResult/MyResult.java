package szx.JsonResult;

public enum MyResult {

    success("success"),
    userNameError("用户名已存在");



    private final String msg;


    MyResult( String msg) {

        this.msg = msg;
    }



    public String getMsg() {
        return msg;
    }

}
