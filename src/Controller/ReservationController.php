<?php

namespace App\Controller;

use App\Entity\Chambre;
use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\ChambreRepository;
use App\Repository\ReservationRepository;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ReservationController extends AbstractController
{
    /**
     * @Route("/admin/reservation", name="reservation_admin")
     */
    public function index(ReservationRepository $repository): Response
    {
        $reservations = $repository->findAll();
        return $this->render('reservation/index.html.twig', [
            'reservation'=>$reservations
        ]);
    }

    /**
     * @Route("/reserver/{id}",name="reserver")
     */
    public function reserverChambre(Chambre $chambre ,Request $request)
    {

        $reservation = new Reservation();
        $modif = false;
        $form=$this->createForm(ReservationType::class,$reservation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $reservation->setUtilisateur($this->getUser());
            $reservation->setChambre($chambre);
            $chambre->setQuantite($chambre->getQuantite() - $reservation->getQuantite());
            $em = $this->getDoctrine()->getManager();
            $em->persist($reservation);
            $this->addFlash('success', "La Reservation a ete effectué");
            $em->flush();
            return $this->redirectToRoute('reservationfront');
        }
        return $this->render('reservation/ajouterreservation.html.twig', [

            "form"=> $form->createView(),
            "modif"=>$modif
        ]);
    }

    /**
     * @Route("/pdf/{id}", name="pdf",methods={"GET"})
     */
    public function pdf(ReservationRepository $repository , $id)
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        $reservation =$repository->find($id);

        // Retrieve the HTML generated in our twig file

        $html = $this->renderView('reservation/pdfreservation.html.twig', [
            "r"=>$reservation
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


    /* ******** back */
    /**
     * @Route("/reservation", name="reservationfront")
     */
    public function indexFront(ReservationRepository $repository): Response
    {
        $reservations = $repository->findAll();
        return $this->render('reservation/indexfront.html.twig', [
            'reservation'=>$reservations
        ]);
    }

}
