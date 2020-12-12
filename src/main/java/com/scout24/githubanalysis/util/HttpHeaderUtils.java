package com.scout24.githubanalysis.util;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public final class HttpHeaderUtils {

    public static Integer parseLinkHttpHeaderAndFetchLastPageCount(String linkHeader) {

        if (linkHeader != null) {
            String[] links = linkHeader.split(",");
            for (String link : links) {
                String[] segments = link.split(";");
                if (segments.length < 2)
                    continue;

                String linkPart = segments[0].trim();
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) {
                    continue;
                }
                linkPart = linkPart.substring(1, linkPart.length() - 1);

                for (int i = 1; i < segments.length; i++) {
                    String[] rel = segments[i].trim().split("=");
                    if (rel.length < 2 || !"rel".equals(rel[0]))
                        continue;

                    String relValue = rel[1];
                    if (relValue.startsWith("\"") && relValue.endsWith("\""))
                        relValue = relValue.substring(1, relValue.length() - 1);

                    if ("last".equals(relValue)) {
                        MultiValueMap<String, String> parameters =
                                UriComponentsBuilder.fromUriString(linkPart).build().getQueryParams();
                        return Integer.valueOf(parameters.get("page").get(0));
                    }
                }
            }
        }

        return null;
    }
}
