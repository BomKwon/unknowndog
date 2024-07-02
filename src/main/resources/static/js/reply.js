let replyservice = (function () {
  let list =  function (page, url, num, callback) {
        $.getJSON(url + num, {page : page}, function (data) {

            callback(data)

        })
  }

  let register = function (reply, url, callback) {
    console.log("진입")
        $.post({
                url : url,
                data : JSON.stringify(reply), //json 문자열
                contentType : "application/json; charset=utf-8"


        }, function (data) {
            console.log(data)
            callback(data) //콜백함수로 콘솔창에 data 출력
        }).fail(function(data) {

            if(data.status == '401'){
                alert("로그인 후 이용해주세요");
                location.href = "/user/login";
            }else {
                console.log(result.status + "  " + result.error)
                alert(result.responseText);
            }
        });
  }

  let modify = function (reply, url,  callback) {

    $.ajax({
        url : url + reply.rno,
        data : JSON.stringify(reply),
        type : 'put',
        contentType: 'application/json; charset=utf-8',
        success : function (result) {
              callback(result)
        },
        error : function (result, status, error) {
            if(result.status == '401'){
                alert("로그인 후 이용해주세요");
                location.href = "/user/login";
            }else {
                console.log(result.status + "  " + result.error)
                alert(result.responseText);
            }

        }

    });

  }

  let read = function (rno, url,  callback) {
      $.get( url + rno, function (data) {
            callback(data)
      })
  }

  let remove = function (rno, url, callback) {
      $.ajax({
          url : url + rno,
          type : 'delete',
        dataType : 'json',
        success : function (data) {
                callback(data)
        },
        error : function (result, status, error) {
                alert(result.status + " " + result.error)
            if(result.status == '401'){
                alert("로그인 후 이용해주세요");
                location.href = "/user/login";
            }else {
                console.log(result.status + "  " + result.error)
                alert(result.responseText);
            }
        },


      })
  }

  return {
      list : list,
      register : register,
      modify : modify,
      read : read,
      delete : remove
  }


}) ();