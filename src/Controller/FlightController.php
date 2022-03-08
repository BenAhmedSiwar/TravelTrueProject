<?php

namespace App\Controller;

use App\Entity\Flight;
use App\Entity\Reservation;
use App\Form\FlightType;
use App\Repository\FlightRepository;
use App\Repository\ReservationRepository;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class FlightController extends AbstractController
{
    /**
     * @Route("/flight", name="flight")
     */
    public function index(FlightRepository $repository , ReservationRepository $repo): Response
    {
        $flights = $repository->findAll();
        $reservations = $repo->findall();
        return $this->render('flight/index.html.twig',[
            'flights' => $flights,
            'reservations' =>$reservations
        ]);
    }

    /**
     * @Route("/pdf/{id}", name="pdf")
     */
    public function pdf(FlightRepository $repository , $id)
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        $flight=$repository->find($id);

        // Retrieve the HTML generated in our twig file

        $html = $this->renderView('flight/pdf.html.twig', [
            "vol"=>$flight
        ]);


        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);
    }

    /**
     * @Route("/searchajaxflight ", name="searchajaxflight")
     */
    public function searchOffreajax(Request $request)
    {
        $repository = $this->getDoctrine()->getRepository(Flight::class);
        $requestString=$request->get('searchValue');
        $flights = $repository->findfligthyname($requestString);

        return $this->render('flight/flightajax.html.twig', [
            "flights"=>$flights
        ]);
    }


    /**
     * @Route("/admin/reserver/{id}",name="reserver")
     */
    public function reserverFlight(Flight $flight ,Request $request , ReservationRepository $repository)
    {

        $reservation = new Reservation();
        $reservation->setClientname('sirine');
        $reservation->setEmail('sirine.karoui@esprit.tn');
        $reservation->setFlight($flight);
        $flight->setQuantite($flight->getQuantite()-1);
        $em = $this->getDoctrine()->getManager();
        $em->persist($reservation);
        $this->addFlash('success',"La Reservation a ete effectué");
        $em->flush();
        return $this->redirectToRoute('flight');


    }



    /* baaack */

    /**
     * @Route("/admin/flight", name="admin_flight")
     */
    public function indexBack(FlightRepository $repository): Response
    {
        $flights = $repository->findAll();
        return $this->render('flight/admin_index.html.twig',[
            'flights' => $flights
        ]);
    }

    /**
     * @Route("/admin/ajouterFlight", name="ajouterFlight")
     * @Route("/admin/modifierFlight/{id}", name="modifierFlight")
     */

    public function modifer_ajouter_flight(Flight $flight =null,Request $request): Response
    {
        $modif = true;
        if(!$flight)
        {
            $flight = new Flight();
            $modif = false;
        }
        $form=$this->createForm(FlightType::class,$flight);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();
            $em->persist($flight);
            $em->flush();
            $this->addFlash('success',"L'action a ete effectué");
            return $this->redirectToRoute('admin_flight');
        }
        return $this->render('flight/ajouter_modifer_flight.html.twig', [
            "flight"=>$flight,
            "form"=> $form->createView(),
            "modif"=>$modif
        ]);
    }

    /**
     * @Route("/admin/suppFlight/{id}",name="supFlight")
     */
    public function supprimerOffre(Flight $flight ,Request $request)
    {
        if($this->isCsrfTokenValid("SUP".$flight->getId(),$request->get("_token")))
        {
            $em=$this->getDoctrine()->getManager();
            $em->remove($flight);
            $em->flush();
            $this->addFlash('success',"L'action a ete effectué");
            return $this->redirectToRoute('admin_flight');
        }
    }

    /**
     * @Route("/admin/calendrier", name="calendrier")
     */
    public function calendrier(FlightRepository $repository)
    {
        $flights=$repository->findAll();

        $rdvs = [];

        foreach ($flights as $event)
        {
            $rdvs[]=[
                'title'=>$event->getVillearrivee().' ~ '.$event->getVilledepart(),
                'start'=>$event->getDatedepart()->format("Y-m-d"),
                'end'=>$event->getDatearrive()->format("Y-m-d"),
                'backgroundColor'=> 'aquamarine',
                'borderColor'=> 'green',
                'textColor' => 'black'
            ];
        }

        $data = json_encode($rdvs);
        return $this->render('flight/calendrier.html.twig',compact('data'));
    }


}
