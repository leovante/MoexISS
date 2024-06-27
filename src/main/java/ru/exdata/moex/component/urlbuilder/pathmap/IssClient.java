package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Query;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;

public final class IssClient implements Closeable {
  public final HttpClient httpClient;

  public final URI uri;

  public IssClient(final HttpClient httpClient, final URI uri) {
    this.httpClient = httpClient;
    this.uri = uri;
  }

  public Iss iss() {
    return new Iss(this, new Query().addPath("iss"));
  }

  @Override
  public void close() throws IOException {

  }

}
