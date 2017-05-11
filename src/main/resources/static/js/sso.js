/**
 * Created by liuruijie on 2017/4/12.
 */

auth_url = "/public/login";

LOGIN = function (id,psw,backToUrl,success) {
    AJAX.POST(auth_url,{id:id,password:psw,backToUrl:backToUrl},function (data) {
        success=success||function () {};
        success();
        var token = data.extra.token;
        var hosts = data.extra.hosts;
        // alert(hosts)
        $.cookie("token", token);
        $.each(hosts, function (n, v) {
            $.cookie("token", token,{domain:v});
        });
        window.location.href = data.extra.backToUrl;
    })
};

