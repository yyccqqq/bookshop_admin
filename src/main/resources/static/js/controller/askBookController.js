app.controller("askBookController", function (
  $scope,
  $controller,
  askBookService
) {
  $controller("baseController", { $scope: $scope }); //继承

  $scope.searchEntity = {}; //定义搜索对象

  //搜索
  $scope.search = function (page, rows) {
    askBookService
      .search(page, rows, $scope.searchEntity)
      .success(function (response) {
        $scope.list = response.list;
        $scope.paginationConf.totalItems = response.total; //更新总记录数
      });
  };

  //查询实体
  $scope.findOne = function (id) {
    askBookService.findOne(id).success(function (response) {
      $scope.entity = response;
    });
  };

  //删除实体
  $scope.delete = function () {
    if ($scope.selectIds.length > 0) {
      askBookService.delete($scope.selectIds).success(function (result) {
        if (result.success) {
          $scope.reloadList();
        }
      });
    } else {
      layer.msg("请选择");
    }
  };
});
