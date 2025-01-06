var main = {

    init : function () {
        let _this = this;

        // 초깃값 셋팅
        $('.select-update-user-role').each(function() {
            $(this).val($(this).attr('role'));
        });

        // 게시글
        $("#btn-register").on('click', function () {
            _this.register();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
         });

        // 회원 정보
        $('.select-update-user-role').on('change', function() {
            _this.updateUserRole($(this));
        });
        $('.btn-delete-user').on('click', function () {
            _this.deleteUser($(this));
        });
    },
    register : function () {
        let data = {
            title : $('#title').val().trim(),
            content : $('#content').val().trim(),
            writerId : $('#writerId').val().trim()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('게시글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        let data = {
            title: $('#title').val().trim(),
            content: $('#content').val().trim()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=uft-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('게시글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        let id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('게시글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    updateUserRole : function (selectElement) {
        let id = $(selectElement).closest('tr').attr('id');
        let role = $(selectElement).val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/users/' + id + '/' + role,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('역할이 수정되었습니다.');
            window.location.href = '/users';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    deleteUser : function (buttonElement) {
        let id = $(buttonElement).closest('tr').attr('id');

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/users/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('회원이 삭제되었습니다.');
            window.location.href = '/users';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();