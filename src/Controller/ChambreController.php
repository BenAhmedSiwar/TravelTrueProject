<?php

namespace App\Controller;

use App\Entity\Chambre;
use App\Form\ChambreType;
use App\Repository\AvisRepository;
use App\Repository\ChambreRepository;
use App\Repository\ReservationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class ChambreController extends AbstractController
{
    /**
     * @Route("/chambre", name="chambre")
     */
    public function index(ChambreRepository $repository , ReservationRepository $repo , AvisRepository $avisRepository): Response
    {
        $chambres = $repository->findAll();
        $reservations = $repo->findBy(['utilisateur'=>$this->getUser()->getId()]);
        $like = $avisRepository->findbylike();
        $dislike = $avisRepository->findbydislike();
        return $this->render('chambre/index.html.twig', [
            'chambres'=>$chambres,
            'reservations'=>$reservations,
            'like'=>$like,
            'dislike'=>$dislike
        ]);
    }

    /**
     * @Route("/searchajaxchambre ", name="searchajaxchambre")
     */
    public function searchOffreajax(Request $request , AvisRepository $avisRepository , ReservationRepository $reservationRepository)
    {
        $reservations = $reservationRepository->findBy(['utilisateur'=>$this->getUser()->getId()]);
        $like = $avisRepository->findbylike();
        $dislike = $avisRepository->findbydislike();
        $repository = $this->getDoctrine()->getRepository(Chambre::class);
        $requestString=$request->get('searchValue');
        $chambres = $repository->findchambreyname($requestString);

        return $this->render('chambre/chambreajax.html.twig', [
            "chambres"=>$chambres,
            'like'=>$like,
            'dislike'=>$dislike,
            'reservations'=>$reservations,
        ]);
    }



    /* baaaaaaaaaack ***/
    /**
     * @Route("/admin/chambre", name="chambre_admin")
     */
    public function indexback(ChambreRepository $repository): Response
    {
        $chambres = $repository->findAll();
        return $this->render('chambre/index_back.html.twig', [
            'chambres'=>$chambres

        ]);
    }


    /**
     * @Route("/admin/ajouterChambre", name="ajouterchambre")
     * @Route("/admin/modifierChambre/{id}", name="modifierchambre")
     */

    public function modifer_ajouter_chambre(Chambre $chambre =null , Request $request): Response
    {
        $modif = true;
        if(!$chambre)
        {
            $chambre = new Chambre();
            $modif = false;
        }
        $form=$this->createForm(ChambreType::class,$chambre);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();
            $em->persist($chambre);
            $em->flush();
            $this->addFlash('success',"L'action a ete effectué");
            return $this->redirectToRoute('chambre_admin');
        }
        return $this->render('chambre/ajouter_modifer_chambre.html.twig', [
            "chambre"=>$chambre,
            "form"=> $form->createView(),
            "modif"=>$modif
        ]);
    }

    /**
     * @Route("/admin/suppChambre/{id}",name="supchambre")
     */
    public function supprimerChambre(Chambre $chambre ,Request $request)
    {
        if($this->isCsrfTokenValid("SUP".$chambre->getId(),$request->get("_token")))
        {
            $em=$this->getDoctrine()->getManager();
            $em->remove($chambre);
            $em->flush();
            $this->addFlash('success',"L'action a ete effectué");
            return $this->redirectToRoute('chambre_admin');
        }
    }

    /**
     * @Route("/reservation/create-checkout-session", name="checkout")
     */
    public function checkout()
    {

        \Stripe\Stripe::setApiKey('sk_test_51ITrmKDemurknTpxCDhbkbloGf2Vp9zDeOfOF80IVNhYUS5RnsYtvcYPYXr1dyygpj70e127PbPpr5HRLqqspqSO00H1gDbJGa');
        $session = \Stripe\Checkout\Session::create([
            'payment_method_types' => ['card'],
            'line_items' => [[
                'price_data' => [
                    'currency' => 'usd',
                    'product_data' => [
                        'name' => 'T-shirt',
                    ],
                    'unit_amount'=> 150,
                ],
                'quantity' => 1,
            ]],
            'mode' => 'payment',
            # These placeholder URLs will be replaced in a following step.
            'success_url'=> $this->generateUrl('success',[],UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('echec',[],UrlGeneratorInterface::ABSOLUTE_URL),
        ]);
        return new JsonResponse(['id'=>$session->id]);
    }

    /**
     * @Route("/reservation/success", name="success")
     */

    public function success(): Response
    {

        return $this->render('reservation/success.html.twig', [

        ]);
    }

    /**
     * @Route("/reservation/echec", name="echec")
     */


    public function echec(): Response
    {
        return $this->render('reservation/echec.html.twig', [

        ]);
    }


}
