package com.aluxian.drizzle.api;

import com.aluxian.drizzle.api.models.Attachment;
import com.aluxian.drizzle.api.models.Bucket;
import com.aluxian.drizzle.api.models.Comment;
import com.aluxian.drizzle.api.models.Credentials;
import com.aluxian.drizzle.api.models.Like;
import com.aluxian.drizzle.api.models.Project;
import com.aluxian.drizzle.api.models.Shot;
import com.aluxian.drizzle.utils.Config;
import com.aluxian.drizzle.utils.UserManager;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Dribbble {

    public static ApiRequest<List<Shot>> listShots(Params.List list, Params.Timeframe timeframe, Params.Sort sort) {
        return new ApiRequest<List<Shot>>()
                .responseType(new TypeToken<List<Shot>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .queryParam("per_page", String.valueOf(Config.RESULTS_PER_PAGE))
                .queryParam("list", list.apiValue)
                .queryParam("timeframe", timeframe.apiValue)
                .queryParam("sort", sort.apiValue)
                .path("shots");
    }

    public static ApiRequest<Shot> getShot(int id) {
        return new ApiRequest<Shot>()
                .responseType(new TypeToken<Shot>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("shots/" + id);
    }

    public static ApiRequest<List<Shot>> listBucketShots(int bucketId) {
        return new ApiRequest<List<Shot>>()
                .responseType(new TypeToken<List<Shot>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .queryParam("per_page", String.valueOf(Config.RESULTS_PER_PAGE))
                .path("buckets/" + bucketId + "/shots");
    }

    /**
     * @param shotId The ID of the shot whose attachments to get.
     * @return The list of attachments for the given shot id.
     */
    public static ApiRequest<List<Attachment>> listAttachments(int shotId) {
        return new ApiRequest<List<Attachment>>()
                .responseType(new TypeToken<List<Attachment>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("shots/" + shotId + "/attachments");
    }

    /**
     * @param shotId The ID of the shot whose rebounds to get.
     * @return The list of rebounds for the given shot id.
     */
    public static ApiRequest<List<Shot>> listRebounds(int shotId) {
        return new ApiRequest<List<Shot>>()
                .responseType(new TypeToken<List<Shot>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("shots/" + shotId + "/rebounds");
    }

    /**
     * @param shotId The ID of the shot whose comments to get.
     * @return The list of comments for the given shot id.
     */
    public static ApiRequest<List<Comment>> listComments(int shotId) {
        return new ApiRequest<List<Comment>>()
                .responseType(new TypeToken<List<Comment>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("shots/" + shotId + "/comments");
    }

    /**
     * @param shotId The ID of the shot whose likes to get.
     * @return The list of likes for the given shot id.
     */
    public static ApiRequest<List<Like>> listLikes(int shotId) {
        return new ApiRequest<List<Like>>()
                .responseType(new TypeToken<List<Like>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("shots/" + shotId + "/likes");
    }

    /**
     * @param shotId The ID of the shot whose buckets to get.
     * @return The list of buckets for the given shot id.
     */
    public static ApiRequest<List<Bucket>> listBuckets(int shotId) {
        return new ApiRequest<List<Bucket>>()
                .responseType(new TypeToken<List<Bucket>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("shots/" + shotId + "/buckets");
    }

    /**
     * @param shotId The ID of the shot whose projects to get.
     * @return The list of projects for the given shot id.
     */
    public static ApiRequest<List<Project>> listProjects(int shotId) {
        return new ApiRequest<List<Project>>()
                .responseType(new TypeToken<List<Project>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("shots/" + shotId + "/projects");
    }

    /**
     * @param userId The ID of the user whose shots to get.
     * @return The list of shots for the given user id.
     */
    public static ApiRequest<List<Shot>> listUserShots(int userId) {
        return new ApiRequest<List<Shot>>()
                .responseType(new TypeToken<List<Shot>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .path("users/" + userId + "/shots");
    }

        /*

        @POST("/shots")
        public Response createShot();

        @PUT("/shots/{id}")
        public Response updateShot(@Path("id") int shotId);

        */

    /*public static ApiRequest<Shot> deleteShot(int id) {
        return new ApiRequest<Shot>()
                .responseType(new TypeToken<Shot>() {})
                .url("/shots/" + id)
                .delete();
    }*/

    public static ApiRequest<Credentials> oauthToken(String code) {
        return new ApiRequest<Credentials>()
                .responseType(new TypeToken<Credentials>() {})
                .url("https://dribbble.com/oauth/token")
                .queryParam("client_id", Config.API_CLIENT_ID)
                .queryParam("client_secret", Config.API_CLIENT_SECRET)
                .queryParam("code", code)
                .post(null);
    }

    public static ApiRequest<String> pixelsDribbbledCount() {
        return new ApiRequest<String>()
                .responseType(new TypeToken<String>() {})
                .useCache(true)
                .url("https://www.kimonolabs.com/api/cfxxcg4u")
                .queryParam("apikey", "hQMs8xI09wAzLH5qYKtTnR5o4Na3qnWI")
                .queryParam("kimmodify", "1");
    }

    public static ApiRequest<List<Shot>> listShotsFromFollowing() {
        return new ApiRequest<List<Shot>>()
                .responseType(new TypeToken<List<Shot>>() {})
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .queryParam("per_page", String.valueOf(Config.RESULTS_PER_PAGE))
                .path("user/following/shots");
    }

    public static <T> ApiRequest<List<T>> listNextPage(String nextPageUrl, TypeToken<List<T>> typeToken) {
        return new ApiRequest<List<T>>()
                .responseType(typeToken)
                .accessToken(UserManager.getInstance().getAccessToken())
                .useCache(true)
                .url(nextPageUrl);
    }

    /**
     * Stores a parsed response from the Dribbble API.
     *
     * @param <T> The type of the expected response data.
     */
    public static final class Response<T> {

        public final T data;
        public final String nextPageUrl;

        public Response(T data, String nextPageUrl) {
            this.data = data;
            this.nextPageUrl = nextPageUrl;
        }

    }

}
