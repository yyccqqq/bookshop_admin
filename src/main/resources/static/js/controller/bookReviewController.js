app.controller("bookReviewController", function (
  $scope,
  $controller,
  bookReviewService
) {
  $controller("baseController", { $scope: $scope }); //继承

  $scope.searchEntity = {}; //定义搜索对象

  //搜索
  $scope.search = function (page, rows) {
    bookReviewService.search(page, rows).success(function (response) {
      $scope.list = response.list;
      $scope.paginationConf.totalItems = response.total; //更新总记录数
    });
  };

  //查询实体
  $scope.findOne = function (id) {
    bookReviewService.findOne(id).success(function (response) {
      $scope.entity = response;
    });
  };

  //批量审核
  $scope.update = function (bookType) {
    if ($scope.selectIds.length > 0) {
      bookReviewService
        .update($scope.selectIds, bookType)
        .success(function (result) {
          if (result.success) {
            $scope.reloadList();
          }
        });
    } else {
      layer.msg("请选择");
    }
  };

  //审核
  $scope.updateOne = function (id, bookType) {
    bookReviewService.updateOne(id, bookType).success(function (result) {
      if (result.success) {
        $scope.reloadList();
      }
    });
  };
});
