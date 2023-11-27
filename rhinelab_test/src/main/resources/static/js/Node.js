var projectNum = $('[name="projectNum"]').val();
$.ajax({
    type: 'POST',
    url: '/accept_con',
    data: { projectNum: projectNum },
    success: function(response) {
        // 请求成功后的处理
    },
    error: function(error) {
        // 请求失败后的处理
    }
});