app.service("loginService", function ($http) {
  this.login = function (entity) {
    return $http.post("admin/login", entity);
  };

  this.logout = function (entity) {
    return $http.get("../admin/logout");
  };
});
