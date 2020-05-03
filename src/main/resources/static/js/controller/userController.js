app.controller("userController", function ($scope, $controller, userService) {
  $controller("baseController", { $scope: $scope }); //继承

  $scope.searchEntity = {}; //定义搜索对象

  //搜索
  $scope.search = function (page, rows) {
    userService
      .search(page, rows, $scope.searchEntity)
      .success(function (response) {
        $scope.list = response.list;
        $scope.paginationConf.totalItems = response.total; //更新总记录数
      });
  };

  //查询实体
  $scope.findOne = function (id) {
    userService.findOne(id).success(function (response) {
      $scope.entity = response;
    });
  };

  //删除实体
  $scope.deleteOne = function (id) {
    userService.deleteOne(id).success(function (response) {
      if (response.success) {
        $scope.reloadList();
      }
    });
  };

  $scope.delete = function () {
    if ($scope.selectIds.length > 0) {
      userService.delete($scope.selectIds).success(function (response) {
        if (response.success) {
          $scope.allSelect = false;
          $scope.reloadList();
        }
      });
    } else {
      layer.msg("请选择");
    }
  };

 
});
