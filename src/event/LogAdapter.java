package event;

import com.jennifersoft.view.adapter.JenniferAdapter;
import com.jennifersoft.view.adapter.JenniferModel;
import com.jennifersoft.view.adapter.model.JenniferEvent;
import com.jennifersoft.view.adapter.util.LogUtil;
import prop.LogProp;
import util.LogConfig;
import util.Logger;
import java.text.SimpleDateFormat;

public class LogAdapter implements JenniferAdapter {
	public void on(JenniferModel[] message) {
		LogProp prop = LogConfig.getLog();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(prop.getDateFormat());

			for(int i = 0; i < message.length; i++) {
				JenniferEvent model = (JenniferEvent) message[i];

				String pattern = prop.getPattern();
				String timeFormat = sdf.format(model.getTime());
				String messageFormat = (model.getMessage() == null || model.getMessage().equals("null")) ? "" : model.getMessage();
				String nameFormat = (!model.getMetricsName().equals("")) ? model.getMetricsName() : model.getErrorType();

				pattern = pattern.replaceFirst("%domainId", "" + model.getDomainId());
				pattern = pattern.replaceFirst("%domainName", "" + model.getDomainName());
				pattern = pattern.replaceFirst("%instanceId", "" + model.getInstanceId());
				pattern = pattern.replaceFirst("%instanceName", "" + model.getInstanceName());
				pattern = pattern.replaceFirst("%value", "" + model.getValue());
				pattern = pattern.replaceFirst("%time", "" + timeFormat);
				pattern = pattern.replaceFirst("%eventName", "" + nameFormat);
				pattern = pattern.replaceFirst("%eventLevel", "" + model.getEventLevel());
				pattern = pattern.replaceFirst("%otype", "" + model.getOtype());
				pattern = pattern.replaceFirst("%serviceName", "" + model.getServiceName());
				pattern = pattern.replaceFirst("%message", "" + messageFormat);
				pattern = pattern.replaceFirst("%detailMessage", "" + model.getDetailMessage());
				pattern = pattern.replaceFirst("%customMessage", "" + model.getCustomMessage());

				Logger.info(pattern);
			}
		} catch (Exception e) {
			LogUtil.error(e.toString());
		}
	}
}
