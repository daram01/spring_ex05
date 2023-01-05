<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp" %>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>
			<div class="panel-body">
				<div class="form-group">
					<label>Bno</label> <input class="form-control" name="bno"
						value='<c:out value="${ board.bno }" />' readonly="readonly" />
				</div>

				<div class="form-group">
					<label>Title</label> <input class="form-control" name="title"
						value='<c:out value="${ board.title }" />' readonly="readonly" />
				</div>

				<div class="form-group">
					<label>Text area</label>
					<textarea class="form-control" rows="3" name="content"
						readonly="readonly"><c:out value="${ board.content }" />
						</textarea>
				</div>

				<div class="form-group">
					<label>Writer</label> <input class="form-control" name="writer"
						value='<c:out value="${ board.writer }" />' readonly="readonly" />
				</div>
				<button data-oper='modify' class="btn btn-default">Modify</button>
				<button data-oper='list' class="btn btn-info">List</button>
				
				<form id="operForm" action="/board/modify" method="get">
					<input type="hidden" id="bno" name="bno" value='<c:out value="${ board.bno }" />'>
					<input type="hidden" name="pageNum" value='<c:out value="${ cri.pageNum }" />'>
					<input type="hidden" name="amount" value='<c:out value="${ cri.amount }" />'>
					<input type="hidden" name="keyword" value='<c:out value="${ cri.keyword }" />'>
					<input type="hidden" name="type" value='<c:out value="${ cri.type }" />'>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- 댓글 모달 코드 추가 -->
<div class='row'>
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> Reply
				<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
			</div>
			
			<div class="panel-body">
				<ul class="chat">
<!-- 					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">user00</strong>
								<small class="pull-right text-muted">2018-01-01 13:13</small>
							</div>
							<p>Good job!</p>
						</div>
					</li> -->
				</ul>
			</div>
		</div>
	</div>
</div>

<!-- 댓글 추가 모달창 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label>
					<input class="form-control"	name='reply' value='New Reply!!!!'>
				</div>
				<div class="form-group">
					<label>Replyer</label>
					<input class="form-control"	name='replyer' value='Replyer'>
				</div>
				<div class="form-group">
					<label>Reply Date</label>
					<input class="form-control"	name='replyDate' value=''>
				</div>
			</div>
			<div class="modal-footer">
 				<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
				<button id='modalCloseBtn' type='button' class='btn btn-default'>Close</button>
		    </div>
		</div>
	</div>
</div>

<script type="text/javascript" src="/resources/js/reply.js"></script>

<!-- 댓글 전체 목록 보여주기 + 모달창-->
<script>
	$(function(){	
		var bnoValue = '<c:out value="${board.bno}"/>';
  		var replyUL = $(".chat");
  
    	showList(1);
    	
    	function showList(page){
    		replyService.getList({bno:bnoValue,page: page || 1 }, function(list) {
    			 var str="";
     
			     if(list == null || list.length == 0){
			       return;
			     }
			     
			     for (var i = 0, len = list.length || 0; i < len; i++) {
			         str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
			         str +="  <div><div class='header'><strong class='primary-font'>["
			    	   +list[i].rno+"] "+list[i].replyer+"</strong>"; 
			         str +="    <small class='pull-right text-muted'>"
			           +replyService.displayTime(list[i].replyDate)+"</small></div>";
			         str +="    <p>"+list[i].reply+"</p></div></li>";
     			 }
     
    			 replyUL.html(str);
			     
    		});  //end function
    	} //end showList 
    	
    	var modal = $(".modal");
   	    var modalInputReply = modal.find("input[name='reply']");
   	    var modalInputReplyer = modal.find("input[name='replyer']");
   	    var modalInputReplyDate = modal.find("input[name='replyDate']");
   	    
   	    var modalModBtn = $("#modalModBtn");
   	    var modalRemoveBtn = $("#modalRemoveBtn");
   	    var modalRegisterBtn = $("#modalRegisterBtn");
   	    
   	    // 모달창 Close 눌러서 닫아 지게 하는 기능
	   	$("#modalCloseBtn").on("click", function(e){
	     	
	     	modal.modal('hide');
	    });
    	
   	    // 처음 새 댓글을 등록할 때, 날짜, 수정, 삭제 기능은 가려지게 하는 기능
    	$("#addReplyBtn").on("click", function(e){
    	      
    		  modal.find("input").val("");
    	      modalInputReplyDate.closest("div").hide();
    	      modal.find("button[id !='modalCloseBtn']").hide();
    	      
    	      modalRegisterBtn.show();
    	      
    	      $(".modal").modal("show");
    	      
    	});
   	    
   	    // 새로운 댓글을 등록하는 기능 (json 형식 key:value)
    	modalRegisterBtn.on("click", function(e){
    		var reply = {
    				reply: modalInputReply.val(),
    				replyer: modalInputReplyer.val(),
    				bno:bnoValue
    		};
    		replyService.add(reply, function(result){
    			alert(result);
    			modal.find("input").val("");
    			modal.modal("hide");
    			
    			// 댓글 목록 갱신하기
    			showList(1);
    		});
    	});
   	    
   	    // 댓글 조회 클릭 이벤트 처리
   	    // chat의 하위태그인 li에 클릭 이벤트를 준다.
   	    $(".chat").on("click", "li", function(e){
   	    	// this-> 현재 이벤트를 발생시키는 대상 즉 li임. data 속성에 담긴 rno(댓글번호) 를 변수에 담는다.
   	    	var rno = $(this).data("rno");
   	    	replyService.get(rno, function(reply){
   	    		// input 태그의 name이 'reply' 의 값 
   	    		modalInputReply.val(reply.reply);
   	    		modalInputReplyer.val(reply.replyer);
   	    		modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readonly");
   	    		// 읽어온 글번호를 data에 다시 담아준다. (나중에 수정이나 삭제를 해야하기 때문에)
   	    		modal.data("rno", reply.rno);
   	    		
   	    		// 모달의 button 중, id가 modalCloseBtn이 아닌 것들은 모두 숨긴다.
   	    		modal.find("button[id !='modalCloseBtn']").hide();
   	    		// 모달의 modify 버튼 보여주기
   	    		modalModBtn.show();
   	    		// 모달의 remove 버튼 보여주기
   	    		modalRemoveBtn.show();
   	    		
   	    		$(".modal").modal("show");
   	    	});
   	    });
   	    
   	    // 댓글의 수정 이벤트 처리 (JSON 형식)
   	    modalModBtn.on("click", function(e){
   	    	// rno : modal.data("rno")-> 위의 댓글 조회에서 받아온 data속성의 값을 rno의 값으로 지정한다.
   	    	var reply = {rno:modal.data("rno"), reply:modalInputReply.val()};
   	    	replyService.update(reply, function(result){
   	    		alert(result);
   	    		modal.modal("hide");
   	    		showList(1);
   	    	});
   	    });
   	    
   	    // 댓글의 삭제 이벤트 처리
   	 	modalRemoveBtn.on("click", function(e){
   	 		var rno = modal.data("rno");
   	 		replyService.remove(rno, function(result){
   	 			alert(result);
   	 			modal.modal("hide");
   	 			showList(1);
   	 		});
   	 	});
   	    
   	    
	});
</script>	

<script type="text/javascript">
/* console.log("==============");
console.log("JS TEST");

var bnoValue = '<c:out value="${board.bno}"/>'; */
// 댓글 등록
/* replyService.add(
		{reply:"JS Test", replyer:"tester", bno:bnoValue},
		function(result){
			alert("RESULT:" + result);
		}
); */

// 댓글 전체 목록
/* replyService.getList({bno:bnoValue, page:1}, function(list){
	for(var i = 0, len = list.length||0; i < len; i++){
		console.log(list[i]);
	}
}); */

// 댓글 삭제와 갱신
/* replyService.remove(5, function(count){
	console.log(count);
	if(count === "success"){
		alert("REMOVED");
	}
}, function(err){
	alert('ERROR...');
}); */

// 댓글 수정
/* replyService.update({
	rno : 6,
	bno : bnoValue,
	reply : "Modified Reply..."
}, function(result){
	alert("수정완료...");
}); */

// 하나의 댓글 조회
/* replyService.get(10, function(data){
	console.log(data);
}); */
</script>

<script type="text/javascript">
$(document).ready(function(){
	let operForm = $("#operForm"); // 폼 객체 받아오기. 
	$("button[data-oper='modify']").on("click", function(e){
		operForm.attr("action","/board/modify").submit();
	});
	
	$("button[data-oper='list']").on("click", function(e){
		operForm.find("#bno").remove(); // 클라이언트가 list로 이동하는 경우에는 특정 번호가 필요하지 않으므로 form 태그 내의 bno태그를 지운다.
		operForm.attr("action", "/board/list")
		operForm.submit();
	});
});
</script>


<%@ include file="../includes/footer.jsp" %>