
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
			
package com.adito.tunnels.actions;

import com.adito.boot.Util;
import com.adito.policyframework.LaunchSession;
import com.adito.policyframework.Resource;
import com.adito.policyframework.actions.AbstractRedirectLaunchAction;
import com.adito.security.SessionInfo;
import com.adito.tunnels.TunnelPlugin;

/**
 * Implementation of {@link com.adito.core.actions.AuthenticatedAction}
 * that launches a <i>Network Place</i>.
 */
public class LaunchTunnelAction extends AbstractRedirectLaunchAction {

    /**
     * Constructor.
     */
    public LaunchTunnelAction() {
        super(TunnelPlugin.SSL_TUNNEL_RESOURCE_TYPE, SessionInfo.MANAGEMENT_CONSOLE_CONTEXT | SessionInfo.USER_CONSOLE_CONTEXT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.policyframework.actions.AbstractRedirectLaunchAction#doPrepareLink(com.adito.policyframework.LaunchSession,
     *      java.lang.String)
     */
    protected String doPrepareLink(LaunchSession launchSession, String returnTo) {
        return "startTunnel.do?launchId=" + launchSession.getId() + "&returnTo=" + Util.urlEncode(returnTo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.policyframework.actions.AbstractLaunchAction#isAgentRequired(com.adito.policyframework.Resource)
     */
    protected boolean isAgentRequired(Resource resource) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.policyframework.actions.AbstractRedirectLaunchAction#isDirectLink(com.adito.policyframework.LaunchSession)
     */
    protected boolean isDirectLink(LaunchSession launchSession) {
        return true;
    }
}
