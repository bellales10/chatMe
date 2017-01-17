app.controller("ChatCtrl", function($scope,$rootScope ,ChatService) {
    $scope.messages = [];
    $scope.message = "";
    $scope.max = 140;
    
    $scope.addMessage = function() {
      ChatService.send($rootScope.currentUser.username + " : " + $scope.message);
      $scope.message = "";
      
    }
    
    ChatService.receive().then(null, null, function(message) {
      $scope.messages.push(message);
      
    })
      $(function () {
          var getMessageText, message_side, sendMessage;
          message_side = 'right';
          getMessageText = function () {
              var $message_input;
              $message_input = $('.message_input');
              return $message_input.val();
          };
          sendMessage = function (text) {
              var $messages, message;
              if (text.trim() === '') {
                  return;
              }
              $('.message_input').val('');
              $messages = $('.messages');
              message_side = message_side === 'left' ? 'right' : 'left';
              message = new Message({
                  text: text,
                  message_side: message_side
              });
              message.draw();
              return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
          };
      })

    })
 