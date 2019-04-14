import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import com.google.protobuf.ByteString;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageInAMural {
  public static void main(String... args) throws Exception {
    
	  //example printouts for json-files of detecting multiple images.
	 // PrintStream out = new PrintStream("output2.txt");
	 // String fileName = "C:\\Users\\Madis\\Desktop\\veg.jpg";
	  //detectLocalizedObjects(fileName);
	  
	  /*Getting the mural file location*/
	  String mural = "C:\\Users\\Madis\\Documents\\GitHub\\Pineapple2\\Testing1212\\src\\mixed-fruits.jpg"; 
	  
	  /*Getting the images that someone takes to compare to the mural, In the future this would be continuous or in a while loop, 
	   * but for now adding them in manually */
	  ArrayList<String> images = new ArrayList<String>();
	  
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\apple.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\grape.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\lemon.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\orange.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\strawberry.jpg");
	  
	  /*getting the known objects in the mural*/
	  ArrayList<String> muralResponses = detectLocalizedObjects(mural);
	  
	  int count = 1;
	  
	  //comparing the current image to the mural
	  for(String image:images)
	  {
		  System.out.println("Image " + count + ":");
		 // gets all known objects in current image
		ArrayList<String> imageResponses =   detectLocalizedObjects(image);
		
		/*compares all known objects in image to all known objects in mural. If it exists, it will print it out along with the name */
		for(int i = 0; i<imageResponses.size(); i++ )
		{
			boolean b = false;
			String s = "";
			for(int j = 0; j< muralResponses.size(); j++)
			{
				if(imageResponses.get(i).equals(muralResponses.get(j)))
					{
						b = true;
						s = imageResponses.get(i);
					}
			}
			
			if(b == true)
				System.out.println("The image exists in the mural as "+ s);
			else
				System.out.println("The images DOES NOT exist in the mural");
		
		}
		count++;
	  }
	  
	  
	  
  }
  
  // this method uses the Google Could Vision AI API; sends a request and gets all the object entity names found using the Vision AI. 
  public static ArrayList<String> detectLocalizedObjects(String filePath)
		    throws Exception, IOException {
		  List<AnnotateImageRequest> requests = new ArrayList<>();

		  ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		  Image img = Image.newBuilder().setContent(imgBytes).build();
		  AnnotateImageRequest request =
		      AnnotateImageRequest.newBuilder()
		          .addFeatures(Feature.newBuilder().setType(Type.OBJECT_LOCALIZATION))
		          .setImage(img)
		          .build();
		  requests.add(request);

		  // Perform the request
		  try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
		    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    List<AnnotateImageResponse> responses = response.getResponsesList();

		    // Display the results
		   /* for (AnnotateImageResponse res : responses) {
		      for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) {
		        out.format("Object name: %s\n", entity.getName());
		        out.format("Confidence: %s\n", entity.getScore());
		        out.format("Normalized Vertices:\n");
		        entity
		            .getBoundingPoly()
		            .getNormalizedVerticesList()
		            .forEach(vertex -> out.format("- (%s, %s)\n", vertex.getX(), vertex.getY()));
		    
		      }*/
		    
		    /*puts all entity names into an array list to return to main to compare with*/
		    ArrayList<String> names = new ArrayList<String>();
		    for (AnnotateImageResponse res : responses) 
		    {
			      for (LocalizedObjectAnnotation entity : res.getLocalizedObjectAnnotationsList()) 
			      {
			        names.add(entity.getName());
			      }
			}
		    
		    return names;
		  }
		}
}