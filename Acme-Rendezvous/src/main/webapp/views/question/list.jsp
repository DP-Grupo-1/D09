<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="questions" requestURI="${requestURI}" id="row">
	<%-- <security:authentication property="principal" var ="loggedactor"/> --%>
	
	<spring:message code="question.questionToAnswer" var="questionToAnswerHeader" />
	<display:column property="questionToAnswer" title="${questionToAnswerHeader}" sortable="true" />
	
	<jstl:if test="${row.creator.userAccount.username==loggedactor.username}">
	<display:column>
	
		<a href="question/user/edit.do?questionId=${row.id}"><spring:message code="question.edit"/></a>
	
	
	</display:column>
	</jstl:if>	
	
	
	<display:column>
	
		<a href="question/answers.do?questionId=${row.id}"><spring:message code="question.answers"/></a>
	
	
	</display:column>	
	

</display:table>