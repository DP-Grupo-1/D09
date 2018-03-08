<!-- list.jsp -->

<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<display:table name="users" id="row" class="displayTag"
	requestURI="${requestURI}" keepStatus="true"
	pagesize="5" >
	
	<display:column>
		<a href="user/display.do?userId=${row.id}"> <spring:message
				code="user.display" />
		</a>
	</display:column>
	
	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="user.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />

	<spring:message code="user.postalAddress" var="postalAddressHeader" />
	<display:column property="postalAddress" title="${postalAddressHeader}" sortable="true" />

	<spring:message code="user.phoneNumber" var="phoneNumberHeader" />
	<display:column property="phoneNumber" title="${phoneNumberHeader}" sortable="true" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

</display:table>
