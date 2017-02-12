package myRESTwsWeb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import myRESTwsBean.BeanDesc;
import myRESTwsBean.BeanVid;
import myRESTwsBean.BeanJSon;

import model.Video;
import model.VideoManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestScoped
@Path("")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class MyResource{
	
	@Inject
	VideoManager vm;
	
    Map<Integer,String> descriptions = new HashMap<>();
    Map<String,List<Integer>> clientDescriptions = new HashMap<>();
    Map<Integer, byte[]> videos = new HashMap<>();
    
    
    @POST
    @Path("/desc")
    public Response postIDD(BeanDesc bean) throws IOException{
    	String resultat = "id client: " + bean.getClient() + " description: "+ bean.getDesc()+" id desc: "+bean.getId();
    	vm.createDesc(bean.getId(), bean.getClient(), bean.getDesc());
    	System.out.println("desc before "+ bean.getClient());
    	return Response.status(201).entity(resultat).build();	
    }
    
    @GET
    @Path("/desc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDescId(@PathParam("id") int id) throws IOException{
    	String sol = vm.getVideoDesc(id);
    	System.out.println("desc: "+sol);
    	return sol;
    }
	
    
    @GET
    @Path("client/{id}/descs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getDescsClient(@PathParam("id") String id) throws IOException{
    	System.out.println("entra en el list");
    	List<String> desc = vm.getUserDesc(id);
    	return desc;
    }
    @GET
    @Path("client/descs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getDescs() throws IOException{
    	System.out.println("entra en el list");
    	List<String> desc = vm.getAllDesc();
    	return desc;
    }
    
    @POST
    @Path("/video")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postVideo(BeanVid bean) throws IOException{
    	String resultat = "id client: " + bean.getClient() + " title: "+ bean.getTitle()+" video: "+bean.getBytes();
    	vm.createVideo(bean.getClient(), bean.getBytes(),bean.getTitle());
    	return Response.status(201).entity(resultat).build();
    }
	

}
