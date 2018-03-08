<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="answers" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
		<spring:message code="answer.written" var="writtenHeader" />
		<display:column property="written" title="${writtenHeader}" sortable="true" />
	
		<spring:message code="answer.answerer" var="answererHeader" />
		<display:column property="answerer.userAccount.username" title="${answererHeader}" sortable="true" />
</display:table>