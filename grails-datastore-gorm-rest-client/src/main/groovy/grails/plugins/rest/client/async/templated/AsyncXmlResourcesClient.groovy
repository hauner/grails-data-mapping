/* Copyright (C) 2013 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugins.rest.client.async.templated

import grails.converters.XML
import grails.plugins.rest.client.async.AbstractAsyncResourcesClient
import grails.plugins.rest.client.async.AsyncRestBuilder
import groovy.transform.CompileStatic
import groovy.util.slurpersupport.GPathResult
import org.springframework.http.MediaType

/**
 * Asynchronous version of the {@link grails.plugins.rest.client.templated.XmlResourcesClient} class
 *
 * @author Graeme Rocher
 * @since 1.0
 */
@CompileStatic
class AsyncXmlResourcesClient extends AbstractAsyncResourcesClient<GPathResult> {
    /**
     * @param url The base URL. Example http://localhost:8080/books
     */
    AsyncXmlResourcesClient(String url) {
        super(url)
    }

    AsyncXmlResourcesClient(String url, AsyncRestBuilder restBuilder) {
        super(url, restBuilder)
    }

    @Override
    Class getAcceptType() { XML }

    @Override
    String getAcceptContentType() { MediaType.APPLICATION_XML_VALUE }
}
