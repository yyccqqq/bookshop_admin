app.controller("loginController", function ($scope, loginService) {
  $scope.entity = {};

  $scope.admin = Cookies.get("admin");

  //登录
  $scope.login = function () {
    var username = $scope.entity.username;
    var password = $scope.entity.password;
    if (
      typeof username == "undefined" ||
      username == "" ||
      typeof password == "undefined" ||
      password == ""
    ) {
      layui.use("layer", function () {
        var layer = layui.layer;
        layer.msg("账号和密码不能为空");
      });
    } else {
      loginService.login($scope.entity).success(function (response) {
        if (response.success) {
          Cookies.set("admin", username);
          location.href = "admin/index.html";
        } else {
          layui.use("layer", function () {
            var layer = layui.layer;
            layer.msg("账号或密码错误");
          });
        }
      });
    }
  };

  $scope.logout = function () {
    loginService.logout().success(function (rsponse) {
      if (rsponse.success) {
        window.location.href = "../login.html";
      }
    });
  };
});
