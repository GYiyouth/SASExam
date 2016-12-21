<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: geyao
  Date: 2016/12/12
  Time: 下午4:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  $END$
  <form action="refresh.action">
    <input type="submit" value="刷新">
  </form>

  <form action="refreshAll.action">
    <input type="submit" value="向所有机器获取状态">
  </form>

  <form action="clearXML.action">
    <input type="submit" value="清空当前记录">
  </form>

  <s:property value="#application.allbean"/>
  </body>
</html>
