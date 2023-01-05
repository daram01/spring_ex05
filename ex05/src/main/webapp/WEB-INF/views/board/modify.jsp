<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp" %>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Modify</h1>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Modify</div>
			<div class="panel-body">
				<form role="form" action="/board/modify" method="post">
				<!-- 추가 ( 이 부분을 Controller가 받아야 하기 때문에 Controller에 cri 객체를 추가한다. -->
				<input type="hidden" name="pageNum" value='<c:out value="${ cri.pageNum }" />'>
				<input type="hidden" name="amount" value='<c:out value="${ cri.amount }" />'>
				<input type="hidden" name="keyword" value='<c:out value="${ cri.keyword }" />'>
				<input type="hidden" name="type" value='<c:out value="${ cri.type }" />'>
				
					<div class="form-group">
						<label>Bno</label> <input class="form-control" name="bno"
							value='<c:out value="${ board.bno }" />' readonly="readonly" />
					</div>

					<div class="form-group">
						<label>Title</label> <input class="form-control" name="title"
							value='<c:out value="${ board.title }" />' />
					</div>

					<div class="form-group">
						<label>Text area</label>
						<textarea class="form-control" rows="3" name="content"><c:out
								value="${ board.content }" /></textarea>
					</div>

					<div class="form-group">
						<label>Writer</label> <input class="form-control" name="writer"
							value='<c:out value="${ board.writer }" />' readonly="readonly" />
					</div>

					<div class="form-group">
						<label>RegDate</label> <input class="form-control" name="regDate"
							value='<fmt:formatDate pattern="yyyy/MM/dd" value="${ board.regdate }" />'
							readonly="readonly">
					</div>

					<div class="form-group">
						<label>Update Date</label> <input class="form-control"
							name="updateDate"
							value='<fmt:formatDate pattern="yyyy/MM/dd" value="${ board.updateDate }" />'
							readonly="readonly">
					</div>

					<button type="submit" data-oper="modify" class="btn btn-default">Modify</button>
					<button type="submit" data-oper="remove" class="btn btn-danger">Remove</button>
					<button type="submit" data-oper="list" class="btn btn-info">List</button>
				</form>
			</div>
		</div>
	</div>
</div> 

<script type="text/javascript">
$(document).ready(function(){
	let formObj = $("form"); // 폼 태그를 객체 형태로 받아 변수로 저장한다.
	
	$('button').on("click", function(e){ // 버튼을 눌렀을 때 이벤트 발생
		e.preventDefault(); // preventDefault() -> form 안에 submit 역할을 하는 버튼을 눌렀어도 페이지 이동을 실행하지 않게 하고싶을 때. submit은 실행됨.
		
		let operation = $(this).data("oper"); // this -> button이, data의 속성이 oper라는 이름을 가진 것을 변수에 담는다.
		console.log(operation);
		
		if(operation === 'remove'){
			formObj.attr("action", "/board/remove");
			
		}else if(operation === 'list'){
			formObj.attr("action", "/board/list").attr("method","get");
			var pageNumTag = $("input[name='pageNum']").clone();
			var amountTag = $("input[name='amount']").clone();
			var keywordTag = $("input[name='keyword']").clone();
			var typeTag = $("input[name='type']").clone();
			
			formObj.empty();
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			formObj.append(keywordTag);
			formObj.append(typeTag);
		}
		
		formObj.submit();
	});
});
</script>
<%@ include file="../includes/footer.jsp" %>