package ru.exdata.moex.component.urlbuilder;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.exdata.moex.component.urlbuilder.response.ResponseMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class IssRequestHelper {

    protected static <T> T get(
            final ResponseMapper<T> responseMapper,
            final URI uri,
            final Query query
    ) {
        final var uriBuilder = new URIBuilder(uri).setPathSegments(query.path());
        query.parameters().forEach(uriBuilder::addParameter);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(uriBuilder.build()))) {

            HttpEntity entity = httpResponse.getEntity();
            String body = EntityUtils.toString(entity);

            return responseMapper.map(body);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
