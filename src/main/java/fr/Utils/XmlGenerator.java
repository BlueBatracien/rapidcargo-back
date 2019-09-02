package fr.Utils;

import fr.Message.MessageRepository;
import fr.Movement.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;

@Controller
public class XmlGenerator {

    @Autowired
    MessageRepository messageRepository;

    private String getNextMessageId() {
        Integer lastid = messageRepository.findTopByOrderByIdDesc().getId();
        lastid++;
        return lastid.toString();
    }

    public void createInputXML(Movement movement) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("CargoMessage");
            doc.appendChild(rootElement);
            rootElement.setAttribute("type", "WarehouseMovement-In");

            Element header = doc.createElement("Header");
            header.setAttribute("from", "RAPIDCARGO");
            header.setAttribute("to", "CARGOINFO");
            header.setAttribute("messageTime", LocalDateTime.now().toString());
            header.setAttribute("messageId", getNextMessageId());
            rootElement.appendChild(header);

            Element warehouseMovementIn = doc.createElement("WarehouseMovementIn");
            rootElement.appendChild(warehouseMovementIn);

            Element movementTime = doc.createElement("movementTime");
            movementTime.appendChild(doc.createTextNode(movement.getRealizedDate().toString()));
            warehouseMovementIn.appendChild(movementTime);

            Element declaredIn = doc.createElement("declaredIn");
            declaredIn.setAttribute("code", movement.getDeclarationPlace().getCode());
            declaredIn.setAttribute("label", movement.getDeclarationPlace().getName());
            warehouseMovementIn.appendChild(declaredIn);

            Element from = doc.createElement("from");
            from.setAttribute("code", movement.getOriginalWarehouse().getCode());
            from.setAttribute("label", movement.getOriginalWarehouse().getName());
            warehouseMovementIn.appendChild(from);

            Element goods = doc.createElement("goods");
            warehouseMovementIn.appendChild(goods);

            Element ref = doc.createElement("ref");
            ref.setAttribute("type", movement.getMerchandiseInfo().getReferenceType().getName());
            ref.setAttribute("code", movement.getMerchandiseInfo().getReference().toString());
            goods.appendChild(ref);

            Element amount = doc.createElement("amount");
            amount.setAttribute("quantity", movement.getMerchandiseInfo().getQuantity().toString());
            amount.setAttribute("weight", movement.getMerchandiseInfo().getWeight().toString());
            goods.appendChild(amount);

            Element description = doc.createElement("description");
            description.appendChild(doc.createTextNode(movement.getMerchandiseInfo().getDescription()));
            goods.appendChild(description);

            Element totalAmount = doc.createElement("totalRefAmount");
            totalAmount.setAttribute("quantity", movement.getMerchandiseInfo().getTotalQuantity().toString());
            totalAmount.setAttribute("weight", movement.getMerchandiseInfo().getTotalWeight().toString());
            goods.appendChild(totalAmount);

            Element customsStatus = doc.createElement("customsStatus");
            customsStatus.appendChild(doc.createTextNode(movement.getCustoms().getStatus()));
            warehouseMovementIn.appendChild(customsStatus);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/tmp/input.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createOutputXML(Movement movement) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("CargoMessage");
            doc.appendChild(rootElement);
            rootElement.setAttribute("type", "WarehouseMovement-Out");

            Element header = doc.createElement("Header");
            header.setAttribute("from", "RAPIDCARGO");
            header.setAttribute("to", "CARGOINFO");
            header.setAttribute("messageTime", LocalDateTime.now().toString());
            header.setAttribute("messageId", getNextMessageId());
            rootElement.appendChild(header);

            Element warehouseMovementOut = doc.createElement("WarehouseMovementOut");
            rootElement.appendChild(warehouseMovementOut);

            Element movementTime = doc.createElement("movementTime");
            movementTime.appendChild(doc.createTextNode(movement.getRealizedDate().toString()));
            warehouseMovementOut.appendChild(movementTime);

            Element declaredIn = doc.createElement("declaredIn");
            declaredIn.setAttribute("code", movement.getDeclarationPlace().getCode());
            declaredIn.setAttribute("label", movement.getDeclarationPlace().getName());
            warehouseMovementOut.appendChild(declaredIn);

            Element to = doc.createElement("to");
            to.setAttribute("code", movement.getDestinationWarehouse().getCode());
            to.setAttribute("label", movement.getDestinationWarehouse().getName());
            warehouseMovementOut.appendChild(to);

            Element goods = doc.createElement("goods");
            warehouseMovementOut.appendChild(goods);

            Element ref = doc.createElement("ref");
            ref.setAttribute("type", movement.getMerchandiseInfo().getReferenceType().getName());
            ref.setAttribute("code", movement.getMerchandiseInfo().getReference().toString());
            goods.appendChild(ref);

            Element amount = doc.createElement("amount");
            amount.setAttribute("quantity", movement.getMerchandiseInfo().getQuantity().toString());
            amount.setAttribute("weight", movement.getMerchandiseInfo().getWeight().toString());
            goods.appendChild(amount);

            Element description = doc.createElement("description");
            description.appendChild(doc.createTextNode(movement.getMerchandiseInfo().getDescription()));
            goods.appendChild(description);

            Element totalAmount = doc.createElement("totalRefAmount");
            totalAmount.setAttribute("quantity", movement.getMerchandiseInfo().getTotalQuantity().toString());
            totalAmount.setAttribute("weight", movement.getMerchandiseInfo().getTotalWeight().toString());
            goods.appendChild(totalAmount);

            Element customsStatus = doc.createElement("customsStatus");
            customsStatus.appendChild(doc.createTextNode(movement.getCustoms().getStatus()));
            warehouseMovementOut.appendChild(customsStatus);

            Element customsDocument = doc.createElement("customsDocument");
            customsDocument.setAttribute("type", movement.getOutputInfo().getCustomsDoc().getStatus());
            customsDocument.setAttribute("ref", movement.getOutputInfo().getCustomsDocRef().toString());
            warehouseMovementOut.appendChild(customsDocument);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/tmp/output.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

}
