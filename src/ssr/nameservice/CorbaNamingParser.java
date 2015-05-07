/**
 * CorbaNamingParser.java
 *
 * @author Yuki Suga (ysuga.net)
 * @date 2011/07/02
 * @copyright 2011, ysuga.net allrights reserved.
 *
 */
package ssr.nameservice;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import jp.go.aist.rtm.RTC.CorbaNaming;

import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import ssr.RTMHelper;
import ssr.rtsbuilder.RTSystemBuilder;
import ssr.rtsprofile.DataPortConnector;
import ssr.rtsprofile.RTComponent;
import RTC.RTObject;

/**
 * @author ysuga
 * 
 */
public class CorbaNamingParser {
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Set<RTComponent> getRegisteredComponentSet(String hostAddress)
			throws Exception {
		Set<RTComponent> componentSet = new HashSet<RTComponent>();
		Set<String> componentUriSet = RTMHelper.getRTObjectPathUriSet(hostAddress);

		String namingUri = hostAddress;
		StringTokenizer tokenizer2 = new StringTokenizer(namingUri, ":");
		if (tokenizer2.countTokens() == 1) {
			namingUri = namingUri + ":2809";
		}

		for (String uri : componentUriSet) {
			RTComponent component = RTSystemBuilder.createComponent(uri);
			if (component != null) {
				componentSet.add(component);
			}
		}

		return componentSet;
	}




	/**
	 * 
	 * @param componentSet
	 * @return
	 * @throws Exception
	 */
	public static Set<DataPortConnector> getConnectorSet(Set<RTComponent> componentSet)
			throws Exception {
		Set<DataPortConnector> connectorSet = new HashSet<DataPortConnector>();

		for (RTComponent component : componentSet) {
			RTObject sourceRTObject = RTSystemBuilder.getComponent(component);

			RTC.PortService[] portServices = sourceRTObject.get_ports();
			for (RTC.PortService portService : portServices) {
				RTC.ConnectorProfile[] connectorProfiles = portService
						.get_connector_profiles();
				for (RTC.ConnectorProfile connectorProfile : connectorProfiles) {
					connectorSet.add(RTSystemBuilder.createConnector(
							componentSet, connectorProfile));
				}
			}
		}
		return connectorSet;
	}

	/**
	 * 
	 * buildRTNamingContext <div lang="ja">
	 * 
	 * @param binding
	 * @param namingContext
	 * @return
	 * @throws Exception
	 *             </div> <div lang="en">
	 * 
	 * @param binding
	 * @param namingContext
	 * @return
	 * @throws Exception
	 *             </div>
	 */
	protected static RTNamingContext buildRTNamingContext(Binding binding,
			NamingContext namingContext) throws Exception {
		RTNamingContext rtNamingContext = new RTNamingContext(
				binding.binding_name[0].id, binding.binding_name[0].kind);

		
		BindingListHolder bl = new BindingListHolder();
		BindingIteratorHolder bi = new BindingIteratorHolder();
		namingContext.list(30, bl, bi);
		for (Binding childBinding : bl.value) {
			if (childBinding.binding_type == BindingType.ncontext) {

				rtNamingContext.add(buildRTNamingContext(childBinding,
						(NamingContext) namingContext
								.resolve(childBinding.binding_name)));
			} else {

				rtNamingContext.add(new RTNamingContext(
						childBinding.binding_name[0].id,
						childBinding.binding_name[0].kind));
			}
		}
		return rtNamingContext;
	}

	/**
	 * 
	 * buildRTNamingContext <div lang="ja">
	 * 
	 * @param nameServerUri
	 * @return
	 * @throws CorbaNamingConnectionException
	 *             </div> <div lang="en">
	 * 
	 * @param nameServerUri
	 * @return
	 * @throws Exception 
	 * @throws InvalidName 
	 * @throws CannotProceed 
	 * @throws NotFound 
	 * @throws CorbaNamingCannotFindException 
	 * @throws CorbaNamingInvalidContextException 
	 * @throws CorbaNamingConnectionException
	 *             </div>
	 */
	public static RTNamingContext buildRTNamingContext(String nameServerUri)
			throws CorbaNamingCannotFindException, CorbaNamingInvalidContextException {
		RTNamingContext rootContext = new RTNamingContext(nameServerUri);

		StringTokenizer tokenizer2 = new StringTokenizer(nameServerUri, ":");
		if (tokenizer2.countTokens() == 1) {
			nameServerUri = nameServerUri + ":2809";
		}
		CorbaNaming corbaNaming = CorbaNamingManager.get(nameServerUri);
		
		NamingContext namingContext = corbaNaming.getRootContext();
		BindingListHolder bl = new BindingListHolder();
		BindingIteratorHolder bi = new BindingIteratorHolder();
		namingContext.list(30, bl, bi);

		for (Binding binding : bl.value) {
			try {
				if (binding.binding_type == BindingType.ncontext) {
					rootContext.add(buildRTNamingContext(binding,
							(NamingContext) namingContext
									.resolve(binding.binding_name)));
				} else {
					rootContext.add(new RTNamingContext(
							binding.binding_name[0].id,
							binding.binding_name[0].kind));
				}
			} catch (Exception e)  {
				throw new CorbaNamingInvalidContextException();
			}
		}

		return rootContext;
	}
}
