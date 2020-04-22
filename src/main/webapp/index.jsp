<%--
  Created by IntelliJ IDEA.
  User: Ashvin
  Date: 4/21/20
  Time: 10:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Web Demo</title>
</head>
<body>
<p>Say <a href="pdf">Hello</a></p>

<form method="post" action="pdf">
  <h2>Name:</h2>
  <input type="text" id="say-hello-text-input" name="name" />
  <input type="submit" id="say-hello-button" value="Say Hello" />
</form>
</body>
</html>
