package au.org.ala.userdetails

import org.springframework.dao.DataIntegrityViolationException

class RoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 100, 1000)
        [roleInstanceList: Role.list(params), roleInstanceTotal: Role.count()]
    }

    def create() {
        [roleInstance: new Role(params)]
    }

    def save() {

        def pattern = ~/[A-Z_]{1,}/

        if(pattern.matcher(params.role).matches()){
            def roleInstance = new Role(params)
            if (!roleInstance.save(flush: true)) {
                render(view: "create", model: [roleInstance: roleInstance])
                return
            }
            flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])
            redirect(action: "show", id: roleInstance.id)
        } else {
            flash.message = 'Role must consist of uppercase characters and underscores only'
            redirect(action: "create")
        }
    }

    def show(Long id) {
        def roleInstance = Role.get(id)
        if (!roleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), id])
            redirect(action: "list")
            return
        }

        [roleInstance: roleInstance]
    }

    def edit(Long id) {
        def roleInstance = Role.get(id)
        if (!roleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), id])
            redirect(action: "list")
            return
        }

        [roleInstance: roleInstance]
    }

    def update(Long id, Long version) {
        def roleInstance = Role.get(id)
        if (!roleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (roleInstance.version > version) {
                roleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'role.label', default: 'Role')] as Object[],
                          "Another user has updated this Role while you were editing")
                render(view: "edit", model: [roleInstance: roleInstance])
                return
            }
        }

        roleInstance.properties = params

        if (!roleInstance.save(flush: true)) {
            render(view: "edit", model: [roleInstance: roleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])
        redirect(action: "show", id: roleInstance.id)
    }

    def delete(Long id) {
        def roleInstance = Role.get(id)
        if (!roleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), id])
            redirect(action: "list")
            return
        }

        try {
            roleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'role.label', default: 'Role'), id])
            redirect(action: "show", id: id)
        }
    }
}
