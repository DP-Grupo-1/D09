<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('USER')">

<display:table name="rendezvouses" id="row" class="displayTag"
	requestURI="${requestURI}" keepStatus="true" pagesize="5">

			<display:column>
				<a href="rendezvous/display.do?rendezvousId=${row.id}">
					<spring:message code="rendezvous.list.display" />
				</a>
			</display:column>

	<spring:message code="rendezvous.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="rendezvous.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="true" />

	<spring:message code="rendezvous.moment" var="momentHeader" />
	<spring:message code="rendezvous.moment.format" var="momentFormat" />
	<display:column property="moment" title="${momentHeader}"
		titleKey="rendezvous.moment" sortable="true"
		format="{0,date,${momentFormat }}" />

	<spring:message code="rendezvous.creator" var="creatorHeader" />
	<display:column title="${creatorHeader}" sortable="true">
		<a href="user/display.do?userId=<jstl:out value="${row.creator.id}"/>">
			<jstl:out value="${row.creator.name} ${row.creator.surname}" />
		</a>
	</display:column>


	<spring:message code="rendezvous.adultOnly" var="adultOnlyHeader" />
	<display:column property="adultOnly" title="${adultOnlyHeader}"
		sortable="true" />

	<spring:message code="rendezvous.flag" var="flagHeader" />
	<display:column property="flag" title="${flagHeader}" sortable="true" />
	
	
	<display:column>
				<a href="comment/list.do?rendezvousId=${row.id}">
					<spring:message code="rendezvous.list.comment" />
				</a>
			</display:column>

<display:column>
	<div>
		<a href="comment/user/create.do?rendezvousId=${row.id}"><spring:message
				code="comment.create" /></a>
	</div>
</display:column>
</display:table>
	
</security:authorize>