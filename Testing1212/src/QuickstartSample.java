import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QuickstartSample {
  public static void main(String... args) throws Exception {
    // Instantiates a client
   /* try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

      // The path to the image file to annotate
      String fileName = "C:\\Users\\Madis\\Desktop\\Scratch_.png";

      // Reads the image file into memory
      Path path = Paths.get(fileName);
      byte[] data = Files.readAllBytes(path);
      ByteString imgBytes = ByteString.copyFrom(data);

      // Builds the image annotation request
      List<AnnotateImageRequest> requests = new ArrayList<>();
      Image img = Image.newBuilder().setContent(imgBytes).build();
      Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
      AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
          .addFeatures(feat)
          .setImage(img)
          .build();
      requests.add(request);

      // Performs label detection on the image file
      BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();

      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          System.out.printf("Error: %s\n", res.getError().getMessage());
          return;
        }

        for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
          annotation.getAllFields().forEach((k, v) ->
              System.out.printf("%s : %s\n", k, v.toString()));
        }
      }
      
      
    }*/
	 // PrintStream out = new PrintStream("output2.txt");
	 // String fileName = "C:\\Users\\Madis\\Desktop\\veg.jpg";
	  //detectLocalizedObjects(fileName);
	  
	  
	  String mural = "C:\\Users\\Madis\\Documents\\GitHub\\Pineapple2\\Testing1212\\src\\mixed-fruits.jpg"; //mural file location
	  ArrayList<String> images = new ArrayList<String>();
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\apple.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\grape.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\lemon.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\orange.jpg");
	  images.add("C:\\\\Users\\\\Madis\\\\Documents\\\\GitHub\\\\Pineapple2\\\\Testing1212\\\\src\\\\strawberry.jpg");
	  ArrayList<String> muralResponses = detectLocalizedObjects(mural);
	  
	  
	  for(String image:images)
	  {
		ArrayList<String> imageResponses =   detectLocalizedObjects(image);
		
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
	  }
	  
	  
	  
  }
  
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