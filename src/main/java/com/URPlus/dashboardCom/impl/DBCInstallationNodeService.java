package com.URPlus.dashboardCom.impl;

import java.util.Locale;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.SystemAPI;
import com.ur.urcap.api.domain.data.DataModel;

public class DBCInstallationNodeService implements SwingInstallationNodeService<DBCInstallationNodeContribution, DBCInstallationNodeView>{

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle(Locale locale) {
		// TODO Auto-generated method stub
		return "Dashboard Com";
	}

	@Override
	public DBCInstallationNodeView createView(ViewAPIProvider apiProvider) {
		// TODO Auto-generated method stub
		SystemAPI systemAPI = apiProvider.getSystemAPI();
		Style style = systemAPI.getSoftwareVersion().getMajorVersion() >= 5? new V5Style() : new V3Style();
		return new DBCInstallationNodeView(style);
	}

	@Override
	public DBCInstallationNodeContribution createInstallationNode(InstallationAPIProvider apiProvider,
			DBCInstallationNodeView view, DataModel model, CreationContext context) {
		// TODO Auto-generated method stub
		return new DBCInstallationNodeContribution(apiProvider, model, view);
	}

}
