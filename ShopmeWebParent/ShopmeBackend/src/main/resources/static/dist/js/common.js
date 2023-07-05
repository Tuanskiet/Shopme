$(function () {
      var list = $('.list-group-action');
        $('.list-group-action').click(function(){
          list.each(function(){
            $(this).removeClass('active');
          });
          $(this).addClass('active');
        });
 });