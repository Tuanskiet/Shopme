$(document).ready(function () {
      // window.onload = function() {
      //   configInput();
      //   initTimer();
      // };
      setTimeout(function(){
        configInput();
        initTimer();
      }, 1000);
   
      configInput();
      function configInput(){
        $('.user-box input').on('input', function() {
          var inputValue = $(this).val();
          if(inputValue != ''){
              $(this).next('label').css({
                  'top': '-20px',
                  'color' : '#d64646',
                  'font-size' : '14px'
                });  
          }else{
              $(this).next('label').css({
                'top': '0px',
                'color' : '#212529',
                'font-size' : '16px'
              });  
          }
        });
      }
      function initTimer(){
            var target_date = new Date("June 20, 2023").getTime();
            // var date = new Date();
            // date.setDate(date.getDate() + 3);
            // var target_date = date.getTime();
            var day, hour, min, sec;
            // var d = $('#day');
            var h = $('#hour');
            var m = $('#minute');
            var s = $('#second');

            setInterval(function(){
                var current_date = new Date().getTime();
                var seconds_left = (target_date - current_date) / 1000;

                day = parseInt(seconds_left/ 86400);
                seconds_left = parseInt(seconds_left % 86400);

                hour = parseInt(seconds_left/ 3600);
                seconds_left = parseInt(seconds_left % 3600);

                min = parseInt(seconds_left/ 60);
                sec = parseInt(seconds_left % 60);

                // d.text(day);
                h.text(hour)
                m.text(min)
                s.text(sec)

                if(hour <10){
                  h.text('0'+hour)
                }
                if(min <10){
                  m.text('0'+min)
                }
                if(sec <10){
                  s.text('0'+sec)
                }

            },1000)

    }
});


