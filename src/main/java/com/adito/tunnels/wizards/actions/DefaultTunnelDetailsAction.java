
				/*
 *  Adito
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package com.adito.tunnels.wizards.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.adito.core.CoreUtil;
import com.adito.policyframework.Permission;
import com.adito.policyframework.PolicyConstants;
import com.adito.security.LogonControllerFactory;
import com.adito.security.SessionInfo;
import com.adito.tunnels.TunnelPlugin;
import com.adito.wizard.AbstractWizardSequence;
import com.adito.wizard.DefaultWizardSequence;
import com.adito.wizard.WizardStep;
import com.adito.wizard.actions.AbstractWizardAction;

/**
 * Action for tunnel resource details.
 */
public class DefaultTunnelDetailsAction extends AbstractWizardAction {

    /**
     * User constant
     */
    public final static String ATTR_USER = "user";

    /**
     * Constructor
     */
    public DefaultTunnelDetailsAction() {
        super(TunnelPlugin.SSL_TUNNEL_RESOURCE_TYPE, new Permission[] { PolicyConstants.PERM_CREATE_EDIT_AND_ASSIGN, PolicyConstants.PERM_PERSONAL_CREATE_EDIT_AND_DELETE });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.core.actions.CoreAction#getNavigationContext(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public int getNavigationContext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return SessionInfo.USER_CONSOLE_CONTEXT | SessionInfo.MANAGEMENT_CONSOLE_CONTEXT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.wizard.actions.AbstractWizardAction#unspecified(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        CoreUtil.addRequiredFieldMessage(this, request);
        return super.unspecified(mapping, form, request, response);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
        return unspecified(mapping, form, request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.wizard.actions.AbstractWizardAction#previous(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward previous(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                    throws Exception {
        throw new Exception("No previous steps.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.wizard.actions.AbstractWizardAction#createWizardSequence(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected AbstractWizardSequence createWizardSequence(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        ActionForward fwd = mapping.findForward("finish");
        DefaultWizardSequence seq = new DefaultWizardSequence(fwd, "tunnels", "tunnelWizard", CoreUtil.getReferer(request),
                        "tunnelWizard", this.getSessionInfo(request));
        seq.putAttribute(ATTR_USER, LogonControllerFactory.getInstance().getUser(request));
        seq.addStep(new WizardStep("/defaultTunnelDetails.do", true));
        seq.addStep(new WizardStep("/tunnelDetails.do"));
        boolean isUserConsole = getSessionInfo(request).isUserConsoleContext();
        seq.addStep(new WizardStep( isUserConsole ? "/tunnelPersonalPolicy.do" : "/tunnelPolicySelection.do"));
        seq.addStep(new WizardStep("/tunnelSummary.do"));
        return seq;
    }
}
