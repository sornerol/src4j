package io.github.sornerol.src4j.client.enums;

import lombok.Getter;

/**
 * Response codes that can be returned by speedrun.com's API.
 */
public enum ResponseCode {
    /**
     * The request was successful.
     */
    OK(200),

    /**
     * This endpoint requires an API token. Either one was not provided, or the provided token was invalid.
     */
    FORBIDDEN(403),

    /**
     * The URL is malformed, or the data requested is not available.
     */
    NOT_FOUND(404),

    /**
     * Speedrun.com is refusing to interpret your request due to rate limits.
     *
     * @see <a href="https://github.com/speedruncomorg/api/blob/master/throttling.md">Speedrun.com's API rate limits</a>
     */
    RATE_LIMIT_EXCEEDED(420);

    @Getter
    private final int value;

    ResponseCode(int value) {
        this.value = value;
    }
}
