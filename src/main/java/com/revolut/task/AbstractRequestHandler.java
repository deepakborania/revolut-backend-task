//package com.revolut.task;
//
//import com.google.gson.Gson;
//import spark.Request;
//import spark.Response;
//import spark.Route;
//
//import java.util.Map;
//
//public abstract class AbstractRequestHandler<V extends Validable, R> implements RequestHandler<V, R>, Route {
//
//    private Class<V> valueClass;
//    private Class<R> respClass;
//
//    private static final int HTTP_BAD_REQUEST = 400;
//
//    public AbstractRequestHandler(Class<V> valueClass, Class<R> respClass) {
//        this.valueClass = valueClass;
//        this.respClass = respClass;
//    }
//
//    public static String dataToJson(Object data) {
//        Gson gson = new Gson();
//        return gson.toJson(data);
//    }
//
//    public final R process(V value, Map<String, String> urlParams) {
//        if (value != null && !value.isValid()) {
//            return respClass.cast(new Answer(HTTP_BAD_REQUEST));
//        } else {
//            return processImpl(value, urlParams);
//        }
//    }
//
//    protected abstract R processImpl(V value, Map<String, String> urlParams);
//
//
//    @Override
//    public R handle(Request request, Response response) throws Exception {
//        try {
//            Gson gson = new Gson();
//            V value = null;
//            if (valueClass != EmptyPayload.class) {
//                value = gson.fromJson(request.body(), valueClass);
//            }
//            Map<String, String> urlParams = request.params();
//            R answer = process(value, urlParams);
//            response.status(answer.getCode());
//            response.type("application/json");
//            response.body(answer.getBody());
//            return answer.getBody();
//        } catch (Exception e) {
//            response.status(400);
//            response.body(e.getMessage());
//            return e.getMessage();
//        }
//    }
//}
