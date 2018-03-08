<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" />
	Acme-Rendezvous Co., Inc.
</b>
<i> <spring:message code="group.data" /> </i>

<p>
	<spring:message code="footer.terms.1" />
	<a href="redirect/terms.do"><spring:message code="footer.terms.2" /></a>
	<spring:message code="footer.terms.3" />
	<a href="redirect/cookies.do"><spring:message code="footer.terms.4" /></a>
</p>