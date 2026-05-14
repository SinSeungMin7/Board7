<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link href="/css/common.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</head>
<body>
  <main>
  <h2>로그인</h2>
  <form action="/Users/Login" method="post">
  <div class="mb-3">
    <label for="userid" class="form-label">아이디</label>
    <input type="text" class="form-control" name="userid" id="userid" value="admin">
  </div>
  <div class="mb-3">
    <label for="passwd" class="form-label">암호</label>
    <input type="password" class="form-control" name="passwd" id="passwd" value="1234">
  </div> 
  <button type="submit" class="btn btn-primary">Submit</button>
</form>
  
  </main>
</body>
</html>