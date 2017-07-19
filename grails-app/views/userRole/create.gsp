<%@ page import="au.org.ala.userdetails.UserRole" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="${grailsApplication.config.skin.layout}">
		<g:set var="entityName" value="${message(code: 'userRole.label', default: 'UserRole')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-userRole" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="create-userRole" class="content scaffold-create" role="main">
			<h1>Add role for ${user}</h1>
			<g:form action="addRole" >
				<fieldset class="form">
					<input type="hidden" id="userId" name="userId" value="${user.id}"/>
					<div class="form-group">
						<label for="role">
                            <g:message code="userRole.role.label" default="Role" />
                        </label>
						<g:select id="role" name="role.id" from="${roles}"
                                  optionKey="role"  value="" class="form-control many-to-one"/>
					</div>

				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="add" class="btn btn-primary save" value="${message(code: 'default.button.add.label', default: 'Add role')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
