package cm_api;

import com.cloudera.api.ClouderaManagerClientBuilder;
import com.cloudera.api.DataView;
import com.cloudera.api.model.ApiHost;
import com.cloudera.api.v10.HostsResourceV10;
import com.cloudera.api.v18.RootResourceV18;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.http.HTTPTransportFactory;

import java.util.List;

/**
 * 测试CM api，获取集群资源占用情况
 */
public class TestCM {

    /**
     * org.apache.cxf.interceptor.Fault:
     * No conduit initiator was found for the namespace http://cxf.apache.org/transports/http.
     */
    static{
        final Bus defaultBus = BusFactory.getDefaultBus();
        final ConduitInitiatorManager extension = defaultBus.getExtension(ConduitInitiatorManager.class);
        extension.registerConduitInitiator("http://cxf.apache.org/transports/http", new HTTPTransportFactory(defaultBus));
    }

    private static RootResourceV18 v18 = new ClouderaManagerClientBuilder()
            .withHost("10.255.25.35")
            .withPort(7180)
            .withUsernamePassword("rd_guest", "Xw5de3dCR63Ex")
            .build().getRootV18();

    /**
     * 获取集群的内存，磁盘占用情况
     */
    public static void getClustersInfo(){
        HostsResourceV10 hostsResourceV10 = v18.getHostsResource();
        List<ApiHost> hostList = hostsResourceV10.readHosts(DataView.SUMMARY).getHosts();
        System.out.println("一共有主机"+hostList.size()+"台");

    }


    public static void main(String[] args) {
        getClustersInfo();
    }
}
